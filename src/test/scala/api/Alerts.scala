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

  val historyResponse = JsonParser.parse("""[
    {
      "friend_info": {
        "verified_account": false,
        "default_lang": "tr_ch",
        "display_name": "\u58b3\u5893\uff08Brian Hsu\uff09",
        "dateformat": 0,
        "nick_name": "brianhsu",
        "has_profile_image": 1,
        "location": "Taipei, Taiwan",
        "bday_privacy": 2,
        "date_of_birth": "Tue, 02 Jan 1990 00:01:00 GMT",
        "karma": 121.16,
        "full_name": "BrianHsu",
        "gender": 1,
        "name_color": null,
        "timezone": "Asia\/Taipei",
        "id": 1367985,
        "avatar": 0
      },
      "type": "friendship_accepted",
      "posted": "Thu, 21 Feb 2013 02:56:59 GMT"
    }

  ]""")

  val successJSON = JsonParser.parse("""{"success_text": "ok"}""")

  override def sendRequest(url: String, method: Verb, 
                           params: (String, String)*): Try[JValue] = {


    def hasUserID(id: Long) = params.contains("user_id" -> id.toString)

    (url, method) match {

      case ("/APP/Alerts/getActive", Verb.GET) => Success(activeResponse)
      case ("/APP/Alerts/getHistory", Verb.GET) => Success(historyResponse)
      case ("/APP/Alerts/addAllAsFan", Verb.POST) => Success(successJSON)
      case ("/APP/Alerts/addAllAsFriends", Verb.POST) => Success(successJSON)
      case ("/APP/Alerts/addAsFan", Verb.POST) if hasUserID(123L) => Success(successJSON)
      case ("/APP/Alerts/addAsFriend", Verb.POST) if hasUserID(456L) => Success(successJSON)
      case ("/APP/Alerts/denyFriendship", Verb.POST) if hasUserID(789L) => Success(successJSON)
      case ("/APP/Alerts/removeNotification", Verb.POST) if hasUserID(1) => Success(successJSON)
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

      val alerts = plurkAPI.Alerts.getHistory.get

      alerts.size should be === 1
      alerts.map(_.alertType) should be === List(FriendshipAccepted)
    }

    it ("accept user as a fan by /APP/Alerts/addAsFan correctly") {
      val isOK = plurkAPI.Alerts.addAsFan(123L).get
      isOK should be === true
    }

    it ("accept user as a friend by /APP/Alerts/addAsFriend correctly") {
      val isOK = plurkAPI.Alerts.addAsFriend(456L).get
      isOK should be === true
    }

    it ("deny user's friend request by /APP/Alerts/denyFriendship correctly") {
      pending
    }

    it ("accept all user's request as fans by /APP/Alerts/addAllAsFan correctly") {
      val isOK = plurkAPI.Alerts.addAllAsFan().get
      isOK should be === true
    }

    it ("accept all users's request as friends by /APP/Alerts/addAllAsFriends correctly") {
      val isOK = plurkAPI.Alerts.addAllAsFriends().get
      isOK should be === true
    }

    it ("remove notification by /APP/Alerts/removeNotification correctly") {
      pending
    }



  }
}

