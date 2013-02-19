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

  override def sendRequest(url: String, method: Verb, 
                           params: (String, String)*): Try[JValue] = {


    def isPlurkID(id: Long) = params.contains("plurk_id" -> id.toString)

    (url, method) match {
      case ("/APP/Timeline/getPlurk", Verb.GET) if isPlurkID(1099209841L) => 
        Success(getPlurkResponse)

      case _ => 
        Failure(throw new Exception("Not implemented"))
    }

  }

}

class TimelineSpec extends FunSpec with ShouldMatchers {

  describe("A PlurkAPI with Timeline trait") {

    val plurkAPI = PlurkAPI.withMock(TimelineAPIMock)

    it ("get plurks by /APP/Polling/getPlurk correctly") {

      val PlurkData(author, users, plurk) = 
        plurkAPI.Timeline.getPlurk(1099209841L).get

      author.id should be === 4460064
      users.get(4460064L).map(_.id) should be === Some(4460064L)
      plurk.plurkID should be === 1099209841L
      plurk.contentRaw should be === Some("RawContent")
    }

  }
}

