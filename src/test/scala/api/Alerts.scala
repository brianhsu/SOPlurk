package org.bone.soplurk.api

import org.bone.soplurk.api.PlurkAPI._
import org.bone.soplurk.oauth.PlurkOAuth
import org.bone.soplurk.oauth.MockOAuth
import org.bone.soplurk.model._
import org.bone.soplurk.constant.AlertType._

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.PrivateMethodTester 

import org.scribe.model.Verb

import net.liftweb.json.JsonAST._
import net.liftweb.json.JsonParser

import scala.util.{Try, Success, Failure}
import java.util.Date

object AlertsAPIMock extends PlurkOAuth(null) with MockOAuth {

  val activeResponse = JsonParser.parse("""[
    {
      "new_fan": {
        "verified_account": false,
        "default_lang": "tr_ch",
        "dateformat": 0,
        "nick_name": "linuxhsu",
        "has_profile_image": 1,
        "location": "Taipei, Taiwan",
        "bday_privacy": 2,
        "date_of_birth": "Wed, 24 Nov 2010 00:00:00 GMT",
        "karma": 0,
        "full_name": "c d hw gee",
        "gender": 1,
        "timezone": null,
        "id": 7462357,
        "avatar": 2
      },
      "type": "new_fan",
      "posted": "Thu, 21 Feb 2013 02:57:00 GMT"
    },
    {
      "from_user": {
        "verified_account": false,
        "default_lang": "tr_ch",
        "dateformat": 0,
        "nick_name": "linuxhsu",
        "has_profile_image": 1,
        "location": "Taipei, Taiwan",
        "bday_privacy": 2,
        "date_of_birth": "Wed, 24 Nov 2010 00:00:00 GMT",
        "karma": 0,
        "full_name": "c d hw gee",
        "gender": 1,
        "timezone": null,
        "id": 7462357,
        "avatar": 2
      },
      "type": "friendship_request",
      "posted": "Thu, 21 Feb 2013 02:56:59 GMT"
    }
  ]""")
  val historyResponse = JsonParser.parse("""{
  }""")

  val successJSON = JsonParser.parse("""{"success_text": "ok"}""")

  override def sendRequest(url: String, method: Verb, 
                           params: (String, String)*): Try[JValue] = {


    def hasUserID(id: Long) = params.contains("user_id" -> id.toString)

    (url, method) match {

      case ("/APP/Alerts/getActive", Verb.GET) => Success(activeResponse)
      case ("/APP/Alerts/getHistory", Verb.GET) => Success(historyResponse)
      case _ => Failure(throw new Exception("Not implemented"))
    }

  }

}

class AlertsSpec extends FunSpec with ShouldMatchers {

  describe("A PlurkAPI with Alerts trait") {

    val plurkAPI = PlurkAPI.withMock(AlertsAPIMock)

    it ("get active alerts by /APP/Alerts/getHistory correctly") {

      val alerts = plurkAPI.Alerts.getActive.get

      alerts.size should be === 2
      alerts.map(_.alertType) should be === List(NewFan, FriendshipRequest)
    }

    it ("get history alerts by /APP/Alerts/getHistory correctly") {
      pending
    }

    it ("accept user as a fan by /APP/Alerts/addAsFan correctly") {
      pending
    }

    it ("accept user as a friend by /APP/Alerts/addAsFriend correctly") {
      pending
    }

    it ("deny user's friend request by /APP/Alerts/denyFriendship correctly") {
      pending
    }

    it ("accept all user's request as fans by /APP/Alerts/addAllAsFan correctly") {
      pending
    }

    it ("accept all users's request as friends by /APP/Alerts/addAllAsFriends correctly") {
      pending
    }

    it ("remove notification by /APP/Alerts/removeNotification correctly") {
      pending
    }



  }
}

