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

object PlurkTopAPIMock extends PlurkOAuth(null) with MockOAuth {

  val collectionJSON = JsonParser.parse("""[
    ["Taiwan", "cn,tr_ch,en", "台灣"], 
    ["English", "en", "English"], 
    ["Philippines", "en,en_fo,ta_fp", "Pilipinas"]
  ]
  """);

  val topicJSON = JsonParser.parse("""[
    [1, "News", "News"], 
    [2, "Entertainment", "Entertainment"], 
    [0, "Interesting", "Interesting"], 
    [5, "Technology", "Technology"]
  ]""");

  val plurksJSON = JsonParser.parse("""{
    "plurk_users": {
        "8945584": {
            "verified_account": false,
            "default_lang": "en",
            "display_name": "мιѕѕ♛ρєтιтє",
            "dateformat": 0,
            "nick_name": "BraeBrae",
            "has_profile_image": 1,
            "location": "",
            "bday_privacy": 1,
            "date_of_birth": "Wed, 13 Apr 1904 00:01:01 GMT",
            "karma": 82.19,
            "full_name": "Ryleigh Adalyn Paige Adare",
            "gender": 0,
            "name_color": null,
            "timezone": null,
            "id": 8945584,
            "avatar": 26
        },
        "9077534": {
            "verified_account": false,
            "default_lang": "en",
            "display_name": "Inner Peace",
            "dateformat": 0,
            "nick_name": "Inner_Peace",
            "has_profile_image": 1,
            "location": "Second life",
            "bday_privacy": 1,
            "date_of_birth": "Mon, 04 Jul 1904 00:01:01 GMT",
            "karma": 69.65,
            "full_name": "Inner Peace",
            "gender": 2,
            "name_color": null,
            "timezone": null,
            "id": 9077534,
            "avatar": 12
        }
    },
    "plurks": [
        {
            "replurkers_count": 3,
            "replurkable": true,
            "favorite_count": 1,
            "is_unread": 0,
            "content": "Content1",
            "vote_user": 0,
            "user_id": 9077534,
            "plurk_type": 0,
            "qualifier_translated": "",
            "replurked": null,
            "poster_uid": 8069251,
            "favorers": [
                9072546
            ],
            "plurk_id": 1100161190,
            "score": 11373.3007812,
            "topic_id": 0,
            "owner_id": 9077534,
            "newly_created": 1,
            "responses_seen": 0,
            "qualifier": ":",
            "replurker_id": null,
            "response_count": 7,
            "limited_to": null,
            "no_comments": 0,
            "posted": "Fri, 22 Feb 2013 01:56:41 GMT",
            "lang": "en",
            "content_raw": "Content1Raw",
            "replurkers": [
                6295579,
                7731272,
                8069251
            ],
            "entry_posted": 1361498870
        },
        {
            "replurkers_count": 2,
            "replurkable": true,
            "favorite_count": 2,
            "is_unread": 0,
            "favorers": [
                7609704,
                9071198
            ],
            "vote_user": 0,
            "user_id": 8945584,
            "plurk_type": 0,
            "replurked": null,
            "poster_uid": 7609704,
            "content": "Content2",
            "plurk_id": 1100143868,
            "score": 11373.3007812,
            "topic_id": 6,
            "owner_id": 8945584,
            "vote_up": 2,
            "responses_seen": 0,
            "qualifier": "shares",
            "replurker_id": null,
            "response_count": 2,
            "limited_to": null,
            "no_comments": 0,
            "posted": "Fri, 22 Feb 2013 00:27:00 GMT",
            "lang": "en",
            "vote_down": 0,
            "content_raw": "Content2Raw",
            "replurkers": [
                8756560,
                9071198
            ],
            "entry_posted": 1361492993
        }
    ],
    "offset": 11373.3007812
  }""")


  override def sendRequest(url: String, method: Verb, 
                           params: (String, String)*): Try[JValue] = {

    def hasCollectionName(name: String) = params.contains("collection_name" -> name)
    def hasOffset(offset: Double) = params.contains("offset" -> offset.toString)
    def hasLimit(limit: Int) = params.contains("limit" -> limit.toString)
    def hasTopic(topicID: Int) = params.contains("topic_filter" -> topicID.toString)
    def hasLang(language: String) = params.contains("lang" -> language)

    (url, method) match {

      case ("/APP/PlurkTop/getCollections", Verb.GET) => Success(collectionJSON)
      case ("/APP/PlurkTop/getTopics", Verb.GET) if hasLang("tr_ch") => Success(topicJSON)

      case ("/APP/PlurkTop/getPlurks", Verb.GET) if hasCollectionName("Taiwan") &&
                                                    hasOffset(123.4) &&
                                                    hasLimit(2) &&
                                                    hasTopic(11) => 
        Success(plurksJSON)


      case _ => Failure(throw new Exception("Not implemented"))
    }

  }

}

class PlurkTopSpec extends FunSpec with ShouldMatchers {

  describe("A PlurkAPI with PlurkTop trait") {

    val plurkAPI = PlurkAPI.withMock(PlurkTopAPIMock)

    it ("get collections by /APP/PlurkTop/getCollections correctly") {
      val collections = plurkAPI.PlurkTop.getCollections.get
      collections should be === List(
        Collection("Taiwan", List("cn", "tr_ch", "en"), "台灣"),
        Collection("English", List("en"), "English"), 
        Collection("Philippines", List("en", "en_fo", "ta_fp"), "Pilipinas")
      )
    }

    it ("get topics by /APP/PlurkTop/getTopics correctly") {

      val topics = plurkAPI.PlurkTop.getTopics("tr_ch").get

      topics should be === List(
        Topic(1, "News", "News"), 
        Topic(2, "Entertainment", "Entertainment"), 
        Topic(0, "Interesting", "Interesting"), 
        Topic(5, "Technology", "Technology")
      )

    }

    it ("get plurks by /APP/PlurkTop/getPlurks correctly") {

      val plurkTopResult = plurkAPI.PlurkTop.getPlurks(
        collectionName = "Taiwan", 
        limit = 2,
        offset = Some(123.4), 
        topicIDFilter = Some(11)
      ).get

      plurkTopResult.users.size should be === 2
      plurkTopResult.plurks.size should be === 2
      plurkTopResult.offset should be === 11373.3007812

    }

  }
}

