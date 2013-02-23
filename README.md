SOPlurk - Scala binding for Plurk API 2.0
==========================================

SOPlurk is an Scala binding for [Plurk API 2.0][0] which use OAuth 1.0 to get authorization from users. It means user don't required to expose thier username / password to the application.

SOPlurk provides a nice and type-safe API to access the Plurk API 2.0, it's very easy to use but also type-safe due to the power Scala has.

You don't need to bother how OAuth works internally, what you need to do is call some API in a certain sequence, and prompt your users to enter the verfication code they get from Plurk.

For example, the following is the complete code to post a plurk to user's timeline. We'll explain the code later, but as you can see, it's quite simple. Most part of code is just to get verfication code from user.

    import scala.util.{Try, Success, Failure}

    import org.bone.soplurk.util.BombTry._
    import org.bone.soplurk.constant.Qualifier

    def logginAPIExample(appKey: String, appSecret: String) {

      val plurkAPI = PlurkAPI.withoutCallback(appKey, appSecret)

      for {
        authURL    <- plurkAPI.getAuthorizationURL.boomIfFailed    // Get authorization URL
        verifyCode <- getVerifcationFromUser(authURL).boomIfFailed // Get verify code from User
        _          <- plurkAPI.authorize(verifyCode).boomIfFailed  // Try to get verified.
      } {

        plurkAPI.Timeline.plurkAdd(
          content = "This is a test plurk posted from SOPlurk",
          qualifier = Qualifier.Says
        ).boomIfFailed

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



Installation
--------------


Using API that doesn't required logged in
-------------------------------------------

Using API that required logged in
-----------------------------------

[0]: http://www.plurk.com/API/2

