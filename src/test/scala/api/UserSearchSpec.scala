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

object UserSearchAPIMock extends PlurkOAuth(null) with MockOAuth {

  val response = JsonParser.parse("""{
    "counts": 779,
    "users": [
        {
            "verified_account": false,
            "last_active": 1361223179,
            "full_name": "Elise Hurley",
            "name_color": "2264D6",
            "timezone": null,
            "num_of_followers": 101,
            "id": 3214200,
            "profile_views": 5551,
            "display_name": "Aranea",
            "date_of_birth": "Sat, 27 May 1989 00:01:00 GMT",
            "location": "Boston, MA, United States",
            "bday_privacy": 2,
            "avatar": 6,
            "default_lang": "en_fo",
            "dateformat": 0,
            "has_profile_image": 1,
            "nick_name": "dreamoflife",
            "gender": 0,
            "karma": 130.2
        },
        {
            "verified_account": false,
            "last_active": 1361291318,
            "full_name": "CHICKEN",
            "name_color": null,
            "timezone": null,
            "num_of_followers": 431,
            "id": 4315078,
            "profile_views": 12730,
            "display_name": "冬眠好ㄘ雞◆",
            "date_of_birth": "Tue, 19 Mar 1991 00:01:00 GMT",
            "location": "Keelung, Taiwan",
            "bday_privacy": 2,
            "avatar": 186,
            "default_lang": "tr_ch",
            "dateformat": 0,
            "has_profile_image": 1,
            "nick_name": "KAMAKA",
            "gender": 2,
            "karma": 129.56
        }
    ],
    "exact_matches": [
        {
            "verified_account": false,
            "default_lang": "en",
            "display_name": "",
            "uid": 958650,
            "relationship": "not_saying",
            "dateformat": 0,
            "nick_name": "ddd",
            "has_profile_image": 0,
            "location": "",
            "bday_privacy": 0,
            "date_of_birth": null,
            "karma": 0,
            "full_name": "gthn eh,by",
            "gender": 1,
            "timezone": null,
            "recruited": 0,
            "id": 958650,
            "email_confirmed": true,
            "avatar": null
        },
        {
            "verified_account": false,
            "default_lang": "en",
            "display_name": "",
            "uid": 2863,
            "relationship": "not_saying",
            "dateformat": 0,
            "nick_name": "Triple_ddd",
            "has_profile_image": 0,
            "location": "",
            "bday_privacy": 0,
            "date_of_birth": null,
            "karma": 0,
            "full_name": "Triple_ddd",
            "gender": 0,
            "timezone": null,
            "recruited": 0,
            "id": 2863,
            "email_confirmed": true,
            "avatar": null
        },
        {
            "verified_account": false,
            "default_lang": "en",
            "display_name": "",
            "uid": 6023,
            "relationship": "not_saying",
            "dateformat": 0,
            "nick_name": "nima",
            "has_profile_image": 0,
            "location": "",
            "bday_privacy": 0,
            "date_of_birth": null,
            "karma": 0,
            "full_name": "nima ddd",
            "gender": 0,
            "timezone": null,
            "recruited": 0,
            "id": 6023,
            "email_confirmed": true,
            "avatar": null
        }
    ]
  }""")

  override def sendRequest(url: String, method: Verb, 
                           params: (String, String)*): Try[JValue] = {


    def hasQuery(query: String) = params.contains("query" -> query)
    def hasOffset(offset: Int) = params.contains("offset" -> offset.toString)

    (url, method) match {

      case ("/APP/UserSearch/search", Verb.GET) if hasQuery("ddd") && hasOffset(10)=> 
        Success(response)

      case _ => 
        Failure(throw new Exception("Not implemented"))
    }

  }

}

class UserSearchSpec extends FunSpec with ShouldMatchers {

  describe("A PlurkAPI with UserSearch trait") {

    val plurkAPI = PlurkAPI.withMock(UserSearchAPIMock)

    it ("get user search result by /APP/UserSearch/search correctly") {
      val result = plurkAPI.UserSearch.search("ddd", 10).get
      result.counts should be === 779
      result.users.size should be === 2
      result.exactMatches.size should be === 3
    }

  }
}

