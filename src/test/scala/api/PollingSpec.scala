package org.bone.soplurk.api

import org.bone.soplurk.api.PlurkAPI._
import org.bone.soplurk.oauth.PlurkOAuth
import org.bone.soplurk.oauth.MockOAuth
import org.bone.soplurk.model._

import org.scalatest.FunSpec
import org.scalatest.Matchers
import org.scalatest.PrivateMethodTester 

import org.scribe.model.Verb

import net.liftweb.json.JsonAST._
import net.liftweb.json.JsonParser

import scala.util.{Try, Success, Failure}

import java.util.Date
import java.util.TimeZone
import java.text.SimpleDateFormat

object PollingAPIMock extends PlurkOAuth(null) with MockOAuth {

  val unreadCountResponse = JsonParser.parse("""{
    "favorite": 1,
    "all": 6,
    "my": 3,
    "responded": 4,
    "private": 5
  }""")

  val plurksResponse = JsonParser.parse("""{
    "plurk_users": {
        "8290019": {
            "verified_account": false,
            "default_lang": "tr_ch",
            "display_name": "YUME",
            "dateformat": 0,
            "nick_name": "likerm6",
            "has_profile_image": 1,
            "location": "Tapei, Taiwan",
            "bday_privacy": 2,
            "date_of_birth": "Fri, 14 Jun 1996 00:01:00 GMT",
            "karma": 100.4,
            "full_name": "Yume 夢夢",
            "gender": 0,
            "name_color": "0A9C17",
            "timezone": null,
            "id": 8290019,
            "avatar": 54
        },
        "3833577": {
            "verified_account": false,
            "default_lang": "tr_ch",
            "display_name": "mingwangx",
            "dateformat": 0,
            "nick_name": "mingwangx",
            "has_profile_image": 1,
            "location": "淡水, Taiwan",
            "bday_privacy": 2,
            "date_of_birth": "Fri, 08 Dec 1978 00:01:00 GMT",
            "karma": 125.34,
            "full_name": "鐘銘",
            "gender": 1,
            "name_color": "0A9C17",
            "timezone": null,
            "id": 3833577,
            "avatar": 70
        }
    },
    "plurks": [
        {
            "replurkers_count": 4,
            "replurkable": true,
            "favorite_count": 4,
            "is_unread": 0,
            "content": "Content",
            "user_id": 1367985,
            "plurk_type": 0,
            "qualifier_translated": "分享",
            "replurked": false,
            "favorers": [
                3898769,
                5220104,
                6053156,
                7241580
            ],
            "replurker_id": null,
            "owner_id": 5530231,
            "responses_seen": 0,
            "qualifier": "shares",
            "plurk_id": 1098983626,
            "response_count": 38,
            "limited_to": null,
            "no_comments": 0,
            "posted": "Mon, 18 Feb 2013 09:05:30 GMT",
            "lang": "tr_ch",
            "content_raw": "Content",
            "replurkers": [
                5608888,
                5618185,
                7241580,
                8296926
            ],
            "favorite": false
        },
        {
            "replurkers_count": 0,
            "replurkable": true,
            "favorite_count": 0,
            "is_unread": 1,
            "content": "Content",
            "user_id": 1367985,
            "plurk_type": 0,
            "qualifier_translated": "說",
            "replurked": false,
            "favorers": [],
            "replurker_id": null,
            "owner_id": 5663569,
            "responses_seen": 0,
            "qualifier": "says",
            "plurk_id": 1098974413,
            "response_count": 1,
            "limited_to": null,
            "no_comments": 0,
            "posted": "Mon, 18 Feb 2013 08:26:40 GMT",
            "lang": "tr_ch",
            "content_raw": "Content",
            "replurkers": [],
            "favorite": false
        },
        {
            "replurkers_count": 0,
            "replurkable": true,
            "favorite_count": 0,
            "is_unread": 0,
            "content": "Content",
            "user_id": 1367985,
            "plurk_type": 0,
            "qualifier_translated": "說",
            "replurked": false,
            "favorers": [],
            "replurker_id": null,
            "owner_id": 3648151,
            "responses_seen": 0,
            "qualifier": "says",
            "plurk_id": 1098944322,
            "response_count": 4,
            "limited_to": null,
            "no_comments": 0,
            "posted": "Mon, 18 Feb 2013 06:11:16 GMT",
            "lang": "tr_ch",
            "content_raw": "Content",
            "replurkers": [],
            "favorite": false
        }
    ]
  }""")

  override def sendRequest(url: String, method: Verb, 
                           params: (String, String)*): Try[JValue] = {

    def isOffset(offset: String) = params contains ("offset", offset)

    (url, method) match {
      case ("/APP/Polling/getUnreadCount", Verb.GET) => 
        Success(unreadCountResponse)

      case ("/APP/Polling/getPlurks", Verb.GET) if isOffset("2013-01-01T00:00:00") => 
        Success(plurksResponse)

      case _ => Failure(throw new Exception("Not implemented"))
    }

  }

}

class PollingSpec extends FunSpec with Matchers {

  describe("A PlurkAPI with Polling trait") {

    val plurkAPI = PlurkAPI.withMock(PollingAPIMock)

    it ("get unread count by /APP/Polling/getUnreadCount correctly") {
      val unreadCount = plurkAPI.Polling.getUnreadCount.get

      unreadCount shouldBe UnreadCount(
        all = 6,
        my = 3,
        privatePlurks = 5,
        responded = 4,
        favorite = 1
      )

    }

    it ("get plurks by /APP/Polling/getPlurks correctly") {

      val dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss Z")
      val offset = dateFormatter.parse("2013-01-01T00:00:00 GMT")
      val Timeline(users, plurks) = plurkAPI.Polling.getPlurks(offset).get

      users.size shouldBe 2
      plurks.size shouldBe 3

    }

  }
}

