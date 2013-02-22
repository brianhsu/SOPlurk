package org.bone.soplurk.model

import org.bone.soplurk.constant._
import org.bone.soplurk.exceptions._
import org.bone.soplurk.model._

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

import net.liftweb.json._

import java.util.Date

class RealtimeResponseSpec extends FunSpec with ShouldMatchers {

  import RealtimeResponseSpec._

  describe("A RealtimeResponse") {

    it ("should able to parse JSON return by realtime channel") {

      val realtimeResponse = RealtimeResponse(realtimeResponseJSON)

      realtimeResponse.user.id should be === 5042068L
      realtimeResponse.response.id should be === 5389539593L
      realtimeResponse.plurk.plurkID should be === 1100202663L

    }


  }
}

object RealtimeResponseSpec {
  val realtimeResponseJSON = JsonParser.parse("""{
    "plurk_id":1100202663,
    "response_count":6,
    "response":{
      "lang":"en",
      "content_raw":"ContentRaw",
      "user_id":5042068,
      "qualifier":":",
      "plurk_id":1100202663,
      "content":"Content",
      "id":5389539593,
      "posted":"Fri, 22 Feb 2013 06:52:44 GMT"
    },
    "user":{
      "5042068":{
        "verified_account":false,
        "default_lang":"ja",
        "display_name":"DisplayName",
        "uid":5042068,
        "dateformat":0,
        "nick_name":"suzaku0503",
        "has_profile_image":1,
        "date_of_birth":"Tue, 03 May 1904 00:01:01 GMT",
        "karma":100.0,
        "gender":1,
        "name_color":"E41227",
        "recruited":1,
        "id":5042068,
        "avatar":140
      }
    },
    "type":"new_response",
    "plurk":{
      "replurkers":[],
      "responses_seen":0,
      "qualifier":"likes",
      "replurkers_count":0,
      "plurk_id":1100202663,
      "response_count":6,
      "replurkable":true,
      "limited_to":null,
      "id":1100202663,
      "favorite_count":2,
      "is_unread":0,
      "lang":"tr_ch",
      "favorers":[3720133,8167122],
      "content_raw":" ContentRaw",
      "user_id":4426947,
      "plurk_type":0,
      "replurked":false,
      "favorite":false,
      "no_comments":0,
      "content":"Content",
      "replurker_id":null,
      "posted":"Fri, 22 Feb 2013 05:08:43 GMT",
      "owner_id":4426947
    }
  }""")
}


