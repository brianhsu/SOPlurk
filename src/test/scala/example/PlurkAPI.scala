package org.bone.soplurk.example

import scala.util.{Try, Success, Failure}

import org.bone.soplurk.api.PlurkAPI
import org.bone.soplurk.api.PlurkAPI.PublicProfile
import org.bone.soplurk.api.PlurkAPI.PlurkResponses

object APIExample {

  
  /**
   *  There are some Plurk API doesn't required access token(user logged in).
   *
   *  For access to these API, just create an instance of `PlurkAPI` by using
   *  `withoutCallback`, and access it directly.
   *  
   *
   */
  def noLogginAPIExample(appKey: String, appSecret: String) {

    //  We are developing console program, so we don't need callback
    //  URL.
    val plurkAPI = PlurkAPI.withoutCallback(appKey, appSecret)

    //
    //  In SOPlurk, API are organized same as the official Plurk 
    //  API (excepted OAuth utils).
    //
    //  For example, if you found there is an Plurk API under
    //  `/APP/Profile/getPublicProfile`, then corresponding SOPlurk
    //  API will under plurkAPI.Profile.getPublicProfile.
    //


    //  Now, we're trying to retrive a public profile of a Plurk
    //  user. 
    //
    //  Not that almost every request API to Plurk in SOPlurk will
    //  return a `Try[T]`, this is very useful, because we don't know
    //  what will go wrong on Internet. 
    //
    //  For example, the user's network maybe not working, or Plurk's 
    //  servers are in maintenance, in this sistuation, it will return
    //  a `Failure` instead of blow up your program, but you still aware
    //  that there is a problem in your request.
    //
    val profile: Try[PublicProfile] = plurkAPI.Profile.getPublicProfile("brianhsu")

    // The body of foreach will only execute if we got profile 
    // correctly.
    profile.foreach { p =>
      println("============ User Profile ============")
      println(s"His nickname:${p.userInfo.basicInfo.nickname}")
      println(s"His relationship:${p.userInfo.relationship}")
      println(s"His fans:${p.fansCount}")
      println(s"His friends:${p.friendsCount}")
      println("======================================")
    }

    //  Since Try[T] is a monad, we could transform it using `map` and chain 
    //  it with for-comprehension.
    //
    //  The following example will print out the response of the lasted Plurk
    //  posted by user `brianhsu`.
    for {
      profile <- plurkAPI.Profile.getPublicProfile("brianhsu")  // Get profile
      plurk   <- Try(profile.plurks.head)                       // Get lastest plurk
      plurkResponse <- plurkAPI.Responses.get(plurk.plurkID)    // Get response of it
    } {
      
      // Now we get friends which is a Map[Long, User], where key is userID
      // and value is user information.
      val PlurkResponses(users, responses, seen) = plurkResponse

      println("========== Lastest Response ==========")

      // So we could print it out.
      responses.foreach { r =>
        val nickname = users(r.userID).nickname
        val qualifier = r.qualifier.name
        val content = r.contentRaw
        
        println(s"$nickname $qualifier $content")
      }

      println("======================================")

    }

    // Or you could use it in a more functional way

    val responses = for {
      profile   <- plurkAPI.Profile.getPublicProfile("brianhsu")  // Get profile
      plurk     <- Try(profile.plurks.head)                       // Get lastest plurk
      plurkResponse <- plurkAPI.Responses.get(plurk.plurkID)      // Get response of it
    } yield {
      
      val PlurkResponses(users, responses, seen) = plurkResponse

      responses.map { r =>
        val nickname = users(r.userID).nickname
        val qualifier = r.qualifier.name
        val content = r.contentRaw
        
        (nickname, qualifier, content)
      }
    }

    //  Now the `response` will be a `Try[List[(String, String, String)]]`, which means
    //  that it either is a Success with a List[(String, String, String)], or it is a
    //  `Failure` with exception.
    //
    //  So we could handle them like the following code:

    responses match {
      case Success(xs) => println(xs.mkString("\n"))
      case Failure(exception) => println("We got exception:" + exception)
    }


  }

  def getVerifcationFromUser(authURL: String): Try[String] = Try {
    
    import java.util.Scanner

    println( "======== Plurk Authorization Instruction ========")
    println(s"1. Please goto the following URL using your favorite browser:")
    println(s"")
    println(s"     $authURL")
    println(s"")
    println(s"2. Hit the `Yes, Grant the permission` button")
    println(s"3. Copy the verifcation number shows in bold text")
    println(s"4. Paste it to this program and hit enter!")
    println( "=================================================")
    print( "Please enter verfication number:")

    val scanner = new Scanner(java.lang.System.in)
    scanner.nextLine()
  }

  def logginAPIExample(appKey: String, appSecret: String) {

    //  Since we are developing console program, we don't need callback URL
    val plurkAPI = PlurkAPI.withoutCallback(appKey, appSecret)

    //
    //  To using API that required user logged in, you need to do the following
    //  things:
    //
    //    1. Get an authorization URL from Plurk.
    //    2. Prompt user to browse the authroization URL you got in step 1.
    //    3. Prompt user to copy the `verification number` after he hit the 
    //       `Yes, Grant the permission` button on that URL.
    //
    //    4. Your program using `PlurkAPI#authroize()` with the verfication number
    //       that user got from step3.
    //
    //    5. Now you can use those logged-in required API as usual.
    //
    //  So the following is how to do this in practice.
    //
    //  Note there is an `boomIfFailed` in the for-comprehension, this is provided
    //  by an implicit class in `BombTry`, it will throw the exception as soon as
    //  possible, so we could know there is something went wrong. (eg. authorization
    //  failed)
    //

    import org.bone.soplurk.util.BombTry._

    for {
      authURL    <- plurkAPI.getAuthorizationURL.boomIfFailed     // Get authorization URL
      verifyCode <- getVerifcationFromUser(authURL).boomIfFailed  // Get verify code from User
      _          <- plurkAPI.authorize(verifyCode).boomIfFailed   // Try to get verified.
    } {

      //  The body of this for-comprehension will only executed if get authorization
      //  successfully, so we could use those API which required logged in.
      //

      //  Post a new Plurk.
      //
      import org.bone.soplurk.constant.Qualifier

      plurkAPI.Timeline.plurkAdd(
        content = "This is a test plurk posted from SOPlurk",
        qualifier = Qualifier.Says
      ).boomIfFailed

      //  Get user's own profile.
      val ownProfile = plurkAPI.Profile.getOwnProfile

      ownProfile.foreach { p =>
        println(s"You have ${p.unreadCount} unread plurks and ${p.alertsCount} alerts")
      }

    }

  }

  def main(args: Array[String]) {

    //
    //  This key and secret it just use to demo how to use SOPlurk,
    //  please register your own Plurk APP key and secret if you 
    //  really want to implement something.
    //
    //  You could request appKey and appSecret at http://www.plurk.com/PlurkApp/
    //
    val appKey = "7xHSzV4xGLJS"
    val appSecret = "r2SgShtAqMP0IWdYHQGYyoi8imWqiw48"

    //  Example of using those API doesn't required user logged in.
    noLogginAPIExample(appKey, appSecret)

    //  Example of using those API required user logged in.
    logginAPIExample(appKey, appSecret)

  }
}
