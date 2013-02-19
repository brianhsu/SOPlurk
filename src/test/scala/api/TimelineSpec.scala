package org.bone.soplurk.api

import org.bone.soplurk.api.PlurkAPI._
import org.bone.soplurk.oauth.PlurkOAuth
import org.bone.soplurk.oauth.MockOAuth
import org.bone.soplurk.model._

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.PrivateMethodTester 

import org.scribe.model.Verb

import net.liftweb.json.JsonAST._
import net.liftweb.json.JsonParser

import scala.util.{Try, Success, Failure}


object TimelineAPIMock extends PlurkOAuth(null) with MockOAuth {

  val getPlurkResponse = JsonParser.parse("""{
    "plurk_users": {
        "4460064": {
            "verified_account": false,
            "default_lang": "tr_ch",
            "display_name": "Users",
            "dateformat": 0,
            "nick_name": "platina6014",
            "has_profile_image": 1,
            "location": "Fatimaid, Taiwan",
            "bday_privacy": 2,
            "date_of_birth": "Mon, 15 Jun 1987 00:01:00 GMT",
            "karma": 114.64,
            "full_name": "Users",
            "gender": 0,
            "name_color": null,
            "timezone": null,
            "id": 4460064,
            "avatar": 86
        }
    },
    "user": {
        "verified_account": false,
        "default_lang": "tr_ch",
        "display_name": "吉他 Tsubaki",
        "dateformat": 0,
        "nick_name": "platina6014",
        "has_profile_image": 1,
        "location": "Fatimaid, Taiwan",
        "bday_privacy": 2,
        "date_of_birth": "Mon, 15 Jun 1987 00:01:00 GMT",
        "karma": 114.64,
        "full_name": "Users",
        "gender": 0,
        "name_color": null,
        "timezone": null,
        "id": 4460064,
        "avatar": 86
    },
    "plurk": {
        "replurkers_count": 0,
        "replurkable": true,
        "favorite_count": 3,
        "is_unread": 0,
        "favorers": [
            4426947,
            5658139,
            8427086
        ],
        "user_id": 4460064,
        "plurk_type": 0,
        "qualifier_translated": "說",
        "replurked": false,
        "content": "QQQQ",
        "replurker_id": null,
        "owner_id": 4460064,
        "responses_seen": 0,
        "qualifier": "says",
        "plurk_id": 1099209841,
        "response_count": 8,
        "limited_to": null,
        "no_comments": 0,
        "posted": "Tue, 19 Feb 2013 01:41:05 GMT",
        "lang": "tr_ch",
        "content_raw": "RawContent",
        "replurkers": [],
        "favorite": false
    }
  }""")

  val getPlurksResponse = JsonParser.parse("""{
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


    def isPlurkID(id: Long) = params.contains("plurk_id" -> id.toString)

    (url, method) match {
      case ("/APP/Timeline/getPlurk", Verb.GET) if isPlurkID(1099209841L) => 
        Success(getPlurkResponse)

      case ("/APP/Timeline/getPlurks", Verb.GET) => 
        Success(getPlurksResponse)

      case _ => 
        Failure(throw new Exception("Not implemented"))
    }

  }

}

class TimelineSpec extends FunSpec with ShouldMatchers {

  describe("A PlurkAPI with Timeline trait") {

    val plurkAPI = PlurkAPI.withMock(TimelineAPIMock)

    it ("get specific plurk by /APP/Polling/getPlurk correctly") {

      val PlurkData(author, users, plurk) = 
        plurkAPI.Timeline.getPlurk(1099209841L).get

      author.id should be === 4460064
      users.get(4460064L).map(_.id) should be === Some(4460064L)
      plurk.plurkID should be === 1099209841L
      plurk.contentRaw should be === Some("RawContent")
    }

    it ("get plurks by /APP/Polling/getPlurks correctly") {

      val Timeline(users, plurks) = plurkAPI.Timeline.getPlurks().get

      users.size should be === 2
      plurks.size should be === 3

    }

  }
}

