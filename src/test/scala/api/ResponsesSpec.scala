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


object ResponsesAPIMock extends PlurkOAuth(null) with MockOAuth {

  val noResponse = JsonParser.parse("""{
    "friends": [],
    "responses_seen": -1,
    "responses": []
  }""")

  val hasResponse = JsonParser.parse("""{
    "friends": {
        "1367985": {
            "verified_account": false,
            "default_lang": "tr_ch",
            "display_name": "墳墓（Brian Hsu）",
            "uid": 1367985,
            "dateformat": 0,
            "nick_name": "brianhsu",
            "has_profile_image": 1,
            "date_of_birth": "Tue, 02 Jan 1990 00:01:00 GMT",
            "karma": 121.12,
            "gender": 1,
            "name_color": null,
            "recruited": 15,
            "id": 1367985,
            "avatar": 0
        }
    },
    "response_count": 2,
    "responses_seen": 2,
    "responses": [
        {
            "lang": "en",
            "content_raw": "啊啊啊，出門時忘記做 git push 啊……",
            "user_id": 1367985,
            "qualifier": "says",
            "plurk_id": 1099531474,
            "content": "啊啊啊，出門時忘記做 git push 啊……",
            "id": 5385889041,
            "posted": "Wed, 20 Feb 2013 01:56:10 GMT"
        },
        {
            "lang": "en",
            "content_raw": "response 2",
            "user_id": 1367985,
            "qualifier": ":",
            "plurk_id": 1099531474,
            "content": "response 2",
            "id": 5385895203,
            "posted": "Wed, 20 Feb 2013 02:01:48 GMT"
        }
    ]
  }""")

  val addResponse = JsonParser.parse("""{
    "lang": "en",
    "content_raw": "啊啊啊，出門時忘記做 git push 啊……",
    "user_id": 1367985,
    "qualifier": "says",
    "plurk_id": 1099531474,
    "content": "啊啊啊，出門時忘記做 git push 啊……",
    "id": 5385889041,
    "posted": "Wed, 20 Feb 2013 01:56:10 GMT"
  }""")

  val deleteResponse = JsonParser.parse("""{"success_text": "ok"}""")


  override def sendRequest(url: String, method: Verb, 
                           params: (String, String)*): Try[JValue] = {


    def hasResponseID(id: Long) = params.contains("response_id" -> id.toString)
    def hasPlurkID(id: Long) = params.contains("plurk_id" -> id.toString)
    def hasContent(content: String, qualifier: String) = {
      params.contains("content" -> content) &&
      params.contains("qualifier" -> qualifier)
    }

    (url, method) match {

      case ("/APP/Responses/get", Verb.GET) if hasPlurkID(0L) => 
        Success(noResponse)

      case ("/APP/Responses/get", Verb.GET) if hasPlurkID(1099531474L) => 
        Success(hasResponse)

      case ("/APP/Responses/responseAdd", Verb.POST) if hasPlurkID(1099531474L) &&
                                                        hasContent("responseTest", "says") =>
        Success(addResponse)

      case ("/APP/Responses/responseDelete", Verb.POST) if hasPlurkID(1099531474L) &&
                                                           hasResponseID(5385889041L) =>
        Success(deleteResponse)

      case _ => 
        Failure(throw new Exception("Not implemented"))
    }

  }

}

class ResponsesSpec extends FunSpec with ShouldMatchers {

  describe("A PlurkAPI with Response trait") {

    val plurkAPI = PlurkAPI.withMock(ResponsesAPIMock)

    it ("get data with no responses by /APP/Responses/get correctly") {

      val responses = plurkAPI.Responses.get(0L).get

      responses should be === PlurkResponses(Map(), Nil, -1)
    }

    it ("get data with responses by /APP/Responses/get correctly") {
      val responses = plurkAPI.Responses.get(1099531474L).get

      responses.friends.size should be === 1
      responses.responses.size should be === 2
      responses.seen should be === 2
    }

    it ("add response by /APP/Responses/responseAdd correctly") {
      pending
    }

    it ("delete response by /APP/Responses/responseAdd correctly") {
      pending
    }

  }
}

