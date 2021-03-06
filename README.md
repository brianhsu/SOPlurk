SOPlurk - Scala binding for Plurk API 2.0
==========================================

Table of Contents
--------------------

  - [Introduction](#introduction)
  - [Installation](#installation)
  - [Design of SOPlurk](#design-of-soplurk)
  - [API Document and Example Code](#api-document-and-example-code)
  - [Using API that doesn't required logged in](#using-api-that-doesnt-required-logged-in)
  - [Using API that required logged in](#using-api-that-required-logged-in)
  - [Authroization in Web Application](#authroization-in-web-application)

Introduction
---------------

SOPlurk is an Scala binding for [Plurk API 2.0][0] which use OAuth 1.0 to get authorization from users. It means user don't required to expose thier username / password to the application.

SOPlurk provides a nice and type-safe API to access the Plurk API 2.0, it's very easy to use but also type-safe due to the power Scala has.

You don't need to bother how OAuth works internally, what you need to do is call some API in a certain sequence, and prompt your users to enter the verfication code they get from Plurk.


### Example: Post a Plurk ####

For example, the following is the complete code to post a plurk to user's timeline. We'll explain the code later, but as you can see, it's quite simple. Most part of code is just to get verfication code from user.

```scala
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
```

### Example: A Plurk bot ###

Thanks for the power of Scala and Akka library, it become very easy to design an Plurk Bot with SOPlurk, the following code is everything you need to create a Plurk bot that accepts all friendship reqeust, and responds to those Plurks contains `hello` in it.

```scala

/**
 *  This bot will accept all friendship request, and responded to 
 *  new Plurks that contains `hello` in it.
 *
 *  It basically a Scala version of http://pastie.org/2765457
 */

import org.bone.soplurk.api._
import org.bone.soplurk.bot._
import org.bone.soplurk.constant._
import org.bone.soplurk.model._

val (appKey, appSecret) = ("APP_KEY", "APP_SECRET")
val (tokenKey, tokenSecret) = ("TOKEN_KEY", "TOKEN_SECRET")

val plurkBot = PlurkBot.withAccessToken(appKey, appSecret, tokenKey, tokenSecret) { 
  plurkAPI: PlurkAPI => {
    case Alert(alertType, user, posted) => plurkAPI.Alerts.addAllAsFriends()
    case RealtimeResponse(user, plurk, response) => // Ignore any response
    case plurk: Plurk =>
      
      if (plurk.content.toLowerCase contains "hello") {
        plurkAPI.Responses.responseAdd(plurk.plurkID, "world", Qualifier.Says)
      }
  }
}

```

Installation
--------------

To using SOPlurk in your Scala project, please add the following defintion in your [SBT][1] `build.sbt ` project. 

Since SOPlurk used `scala.util.Try` which only exists after Scala 2.10.0, you must tell SBT to build your project using Scala 2.10.0 or above.

```scala
// In build.sbt

scalaVersion := "2.11.4"

resolvers += "bone" at "http://brianhsu.moe/ivy"

libraryDependencies += "org.bone" %% "soplurk" % "0.3.5"
```

After done this, enter `reload` and `update` in your SBT console, it will fetch all libraries that need to using SOPlurk.
    
```console
brianhsu@USBGentoo ~/Test $ sbt
[info] Set current project to default-9dcd95 (in build file:/home/brianhsu/Test/)
> reload
[info] Set current project to default-9dcd95 (in build file:/home/brianhsu/Test/)
> update
[info] Updating {file:/home/brianhsu/Test/}default-9dcd95...
[info] Resolving commons-logging#commons-logging;1.0.4 ...
[info] Done updating.
[success] Total time: 2 s, completed 2013/2/23 下午 01:41:12
> 
```

API Document and Example Code
--------------------------------

  - [API Document][1]
  - [Example code with detailed explanation][2]

Design of SOPlurk
--------------------------------

SOPlurk is a project that let me practice how to design Scala library that make client code clean yet robust at first. 

But it turns out Scala is really powerful and so much fun to programming with it, so I've implemented every API you could find in [Plurk API 2.0 Document][0] just in 12 days. In facts, there are even some API that doesn't list on the document is also be implemented in SOPlurk.

### API Layout ###

[PlurkAPI][3] is the marjor class you will dealing with when using SOPlurk. 

To make things more intutive, there are 15 singleton Scala `object` in it, which are corresponding to the second part of URL in official Plurk API.

For example, if you found the following API in Plurk API 2.0 document:

  - `/APP/Timeline/plurkAdd`
  - `/APP/Profile/getOwnProfile`
  - `/APP/Users/currUser`

They will corresponding to the following API at `PlurkAPI` instance, respectively.

  - `PlurkAPI#Timeline.plurkAdd`
  - `PlurkAPI#Profile.getOwnProfile`
  - `PlurkAPI#Users.currUser`

The only exception is those OAuth utils API, they are grouped under `PlurkAPI#OAuthUtils`.

### Leverage with `scala.util.Try[T]` ###

There are many thing could go wrong when we accessing resource from Internet, user's network maybe down, the Plurk's server maybe is crashed and not responding. It's very hard to ensure that everything will go correct as we exepcted.

To solve this and make sure the client code is as robust as possible, almost every API in SOPlurk is returning a `scala.util.Try[T]`.

By using `Try[T]`, the client code will notice there are doing something that could go wrong, and they could choose how to deal with it.

For example, we could choose the good old procedure style, just check if PlurkAPI returned an `TimeInfo` or there is an exception occurs (for example, network is not available).

```scala

import scala.util.{Try, Success, Failure}
import org.bone.soplurk.api.PlurkAPI

val plurkAPI = PlurkAPI.withoutCallback("7xHSzV4xGLJS", "r2SgShtAqMP0IWdYHQGYyoi8imWqiw48")

plurkAPI.OAuthUtils.checkTime match {
  case Success(timeInfo) => println("We got server time from Plurk:" + timeInfo)
  case Failure(exception) => println("Here is an exception:" + exception)
}

```

Sometime we may just want ignore the exception if we're just doing some quick test, in this case, `for` is a good friend.

```scala

import scala.util.{Try, Success, Failure}
import org.bone.soplurk.api.PlurkAPI

val plurkAPI = PlurkAPI.withoutCallback("7xHSzV4xGLJS", "r2SgShtAqMP0IWdYHQGYyoi8imWqiw48")

for (timeInfo <- plurkAPI.OAuthUtils.checkTime) {
  println("We got time from server:" + timeInfo)
}

```

We may do this in functional too, `Try[T]` has a `map()` method just like `Option[T]`, and it similiar to `map()` in `Optiont[T]`. If we have a `Success[T]`, the map will convert `T` to something, but if we have a `Failure`, then the result will still be `Failure`.


```scala

import scala.util.{Try, Success, Failure}
import org.bone.soplurk.api.PlurkAPI

val plurkAPI = PlurkAPI.withoutCallback("7xHSzV4xGLJS", "r2SgShtAqMP0IWdYHQGYyoi8imWqiw48")
val unixTimettamp = plurkAPI.OAuthUtils.checkTime.map(_.unixTimestamp)

```

If you are not familiar with `Try[T]`, we recommanded the following article:

  - [The Neophyte's Guide to Scala Part 6: Error Handling With Try][4]

It's worth to reading and understand how `Try[T]` works, you will love how `Try[T]` could help you handle Exception once you get it IMHO.

### But sometime we hope it just crash -- `BombTry` ###

When using `Try[T]` in the for-comprehension without yield form, if there are `Failure` in the chain, it will simply ignore the body code block. 

It's like a `try-catch` block which swallowed any exception, which is a code smell that almost every code-style book will told you not to do it.

For example, the following code will executed without an error:

```scala
import scala.util._

def operationA: Try[Int] = Success(1)
def operationB: Try[Int] = Failure(new Exception("error"))

for {
  valueA <- operationA
  valueB <- operationB
} {
  println("A:" + valueA)
  println("B:" + valueB)
}
```

It is basically wrapping everything in a try block, and ignore any exceptions:

```scala
import scala.util._

def operationA: Try[Int] = Success(1)
def operationB: Try[Int] = Failure(new Exception("error"))

try {

  val valueA = operationA.get
  val valueB = operationB.get   // This line will throw exception since operationB is a Failure

  println("A:" + valueA)
  println("B:" + valueB)

} catch {

  case _: Exception => // Eat exception

}

```

It's not a good code, because no one will notice we have an exception.

Sometime we hope our program just crash when there are exceptions occurs, so we could find out what's go wrong by dig into the stacktrace.

So here comes `org.bone.soplurk.util.BombTry`, it provides an implicit class which let you use the nice for-comprehension syntax, but throw out the exception as soon as possible.

```scala
import scala.util._
import org.bone.soplurk.util.BombTry._

def operationA: Try[Int] = Success(1)
def operationB: Try[Int] = Failure(new Exception("error"))

for {
  valueA <- operationA.boomIfFailed
  valueB <- operationB.boomIfFailed
} {
  println("A:" + valueA)
  println("B:" + valueB)
}
```

Using API that doesn't required logged in
-------------------------------------------

There are some Plurk API doesn't required access token(user logged in).

For access to these API, just create an instance of `PlurkAPI` by using `withoutCallback`, and access it directly.

```scala
// We are developing console program, so we don't need callback
// URL.

val plurkAPI = PlurkAPI.withoutCallback(appKey, appSecret)
```

Now, we're trying to retrive a public profile of a Plurk user.

Not that almost every request API to Plurk in SOPlurk will return a `Try[T]`, this is very useful, because we don't know what will go wrong on Internet.

For example, the user's network maybe not working, or Plurk's servers are in maintenance, in this sistuation, it will return a `Failure` instead of blow up your program, but you still aware that there is a problem in your request.

```scala
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
```

Since `Try[T]` is a monad, we could transform it using `map` and chain it with for-comprehension.

```scala

// The following example will print out the response of the lasted Plurk
// posted by user `brianhsu`.

for {
  profile <- plurkAPI.Profile.getPublicProfile("brianhsu") // Get profile
  plurk <- Try(profile.plurks.head) // Get lastest plurk
  plurkResponse <- plurkAPI.Responses.get(plurk.plurkID) // Get response of it
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
```

Or you could use it in a more functional way, map it to new values instead of print it out directly.

```scala
val responses = for {
  profile <- plurkAPI.Profile.getPublicProfile("brianhsu") // Get profile
  plurk <- Try(profile.plurks.head) // Get lastest plurk
  plurkResponse <- plurkAPI.Responses.get(plurk.plurkID) // Get response of it
} yield {
  
  val PlurkResponses(users, responses, seen) = plurkResponse

  responses.map { r =>
    val nickname = users(r.userID).nickname
    val qualifier = r.qualifier.name
    val content = r.contentRaw
    
    (nickname, qualifier, content)
  }
}

// Now the `response` will be a `Try[List[(String, String, String)]]`, which means
// that it either is a Success with a List[(String, String, String)], or it is a
// `Failure` with exception.
//
// So we could handle them like the following code:

responses match {
  case Success(xs) => println(xs.mkString("\n"))
  case Failure(exception) => println("We got exception:" + exception)
}
```

Using API that required logged in
-----------------------------------

To using API that required user logged in, you need to do the following things:

1. Get an authorization URL from Plurk.
2. Prompt user to browse the authroization URL you got in step 1.
3. Prompt user to copy the `verification number` after he hit the `Yes, Grant the permission` button on that URL.
4. Your program using `PlurkAPI#authroize()` with the verfication number that user got from step3.
5. Now you can use those logged-in required API as usual.

So the following is how to do this in practice.

Note there is an `boomIfFailed` in the for-comprehension, this is provided by an implicit class in `BombTry`, it will throw the exception as soon as possible, so we could know there is something went wrong. (eg. authorization failed)

```scala

import org.bone.soplurl.api.PlurkAPI
import org.bone.soplurk.util.BombTry._

val plurkAPI = PlurkAPI.withoutCallback(appKey, appSecret)

for {
  authURL <- plurkAPI.getAuthorizationURL.boomIfFailed // Get authorization URL
  verifyCode <- getVerifcationFromUser(authURL).boomIfFailed // Get verify code from User
  _ <- plurkAPI.authorize(verifyCode).boomIfFailed // Try to get verified.
} {

  // The body of this for-comprehension will only executed if get authorization
  // successfully, so we could use those API which required logged in.
  //

  // Post a new Plurk.
  //
  import org.bone.soplurk.constant.Qualifier

  plurkAPI.Timeline.plurkAdd(
    content = "This is a test plurk posted from SOPlurk",
    qualifier = Qualifier.Says
  ).boomIfFailed

  // Get user's own profile.
  val ownProfile = plurkAPI.Profile.getOwnProfile

  ownProfile.foreach { p =>
    println(s"You have ${p.unreadCount} unread plurks and ${p.alertsCount} alerts")
  }

}

def getVerifcationFromUser(authURL: String): Try[String] = Try {
  
  import java.util.Scanner

  println( "======== Plurk Authorization Instruction ========")
  println(s"1. Please goto the following URL using your favorite browser:")
  println(s"")
  println(s" $authURL")
  println(s"")
  println(s"2. Hit the `Yes, Grant the permission` button")
  println(s"3. Copy the verifcation number shows in bold text")
  println(s"4. Paste it to this program and hit enter!")
  println( "=================================================")
  print( "Please enter verfication number:")

  val scanner = new Scanner(java.lang.System.in)
  scanner.nextLine()
}

```

Authroization in Web Application
------------------------------------

The authorization steps in Web application could be a lot simpler if you provide an callback argument to `PlurkAPI`, the user won't need to paste its verifcation number by themselevs.

When you provide `callback` argument to `PlurkAPI`, when user hit the `Yes, grant the permission` button, they will be redirect to the callback URL you set, and the verfication number if passed by the `oauth_verifier` http GET parameter in the URL.

So your code will look like the following:

```scala
//
// In http://localhost/yourApp/login
//

val plurkAPI = PlurkAPI.withCallback(
  appKey    = "yAW0goxD23qF",
  appSecret = "agKpMI6qImQIzhJm11b3t9mvuZo7xpny", 
  callback  = "http://localhost/yourApp/myProfile"
)

for (authURL <- plurkAPI.getAuthorizationURL) {

  // 1. Save `api` to session

  // 2. Redirect user to authURL
  
}

```

```scala
//
// In http://localhost/yourApp/myProfile
//

val plurkAPI = ....  // Get `plurkAPI` from session
val verifyCode = ... // Get `oauth_verifier` URL parameter

plurkAPI.authorize(verifyCode).foreach {

  println(plurkAPI.OAuthUtils.checkToken)

}


```


[0]: http://www.plurk.com/API/2
[1]: http://brianhsu.github.com/SOPlurk/api/index.html
[2]: https://github.com/brianhsu/SOPlurk/blob/develop/src/test/scala/example/PlurkAPI.scala
[3]: http://brianhsu.github.com/SOPlurk/api/index.html#org.bone.soplurk.api.PlurkAPI
[4]: http://danielwestheide.com/blog/2012/12/26/the-neophytes-guide-to-scala-part-6-error-handling-with-try.html

