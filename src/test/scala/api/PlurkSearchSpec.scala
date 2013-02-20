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
import java.util.Date

object PlurkSearchAPIMock extends PlurkOAuth(null) with MockOAuth {

  val response = JsonParser.parse("""{
    "has_more": true,
    "error": null,
    "last_offset": 1074074861,
    "users": {
      "3159520": {
        "verified_account": false,
        "default_lang": "tr_ch",
        "display_name": "Thinker",
        "dateformat": 0,
        "nick_name": "Thinker",
        "has_profile_image": 1,
        "date_of_birth": "Sat, 05 Feb 1977 00:01:00 GMT",
        "karma": 90.46,
        "gender": 1,
        "name_color": null,
        "id": 3159520,
        "avatar": 18
      },
      "3459114": {
        "verified_account": false,
        "default_lang": "tr_ch",
        "display_name": "david.osdc",
        "dateformat": 0,
        "nick_name": "davihuan",
        "has_profile_image": 1,
        "date_of_birth": null,
        "karma": 108.4,
        "gender": 1,
        "name_color": null,
        "id": 3459114,
        "avatar": 50
      }
    },
    "plurks": [
      {
        "replurkers_count": 0,
        "replurkable": true,
        "favorite_count": 0,
        "is_unread": 0,
        "favorers": [],
        "user_id": 3558612,
        "plurk_type": 0,
        "qualifier_translated": "\u8aaa",
        "replurked": false,
        "content": "\u4eca\u5e74OSDC\u4f3c\u4e4e\u5831\u540d\u6709\u9ede\u665a",
        "plurk_id": 1099325092,
        "owner_id": 3558612,
        "responses_seen": 0,
        "qualifier": "says",
        "replurker_id": null,
        "response_count": 1,
        "limited_to": null,
        "no_comments": 0,
        "posted": "Tue, 19 Feb 2013 10:26:23 GMT",
        "lang": "tr_ch",
        "content_raw": "\u4eca\u5e74OSDC\u4f3c\u4e4e\u5831\u540d\u6709\u9ede\u665a",
        "replurkers": [
          
        ],
        "favorite": false
      }
    ]
  }""")

  override def sendRequest(url: String, method: Verb, 
                           params: (String, String)*): Try[JValue] = {


    def hasQuery(query: String) = params.contains("query" -> query)
    def hasOffset(offset: Int) = params.contains("offset" -> offset.toString)

    (url, method) match {

      case ("/APP/PlurkSearch/search", Verb.GET) if hasQuery("osdc") && hasOffset(20)=> 
        Success(response)

      case _ => 
        Failure(throw new Exception("Not implemented"))
    }

  }

}

class PlurkSearchSpec extends FunSpec with ShouldMatchers {

  describe("A PlurkAPI with PlurkSearch trait") {

    val plurkAPI = PlurkAPI.withMock(PlurkSearchAPIMock)

    it ("get search result by /APP/PlurkSearch/search correctly") {
      val result = plurkAPI.PlurkSearch.search("osdc", 20).get

      result.hasMore should be === true
      result.lastOffset should be === 1074074861L
      result.users.size should be === 2
      result.plurks.size should be === 1
    }

  }
}

