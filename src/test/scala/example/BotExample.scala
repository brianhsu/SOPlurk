package org.bone.soplurk.example

import org.bone.soplurk.api._
import org.bone.soplurk.bot._
import org.bone.soplurk.constant._
import org.bone.soplurk.model._

/**
 *  This bot will accept all friendship request, and responded to 
 *  new Plurks that contains `hello` in it.
 *
 *  It basically a Scala version of http://pastie.org/2765457
 *  
 */
object BotExample {

  def main(args: Array[String]) {
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
  }
}
