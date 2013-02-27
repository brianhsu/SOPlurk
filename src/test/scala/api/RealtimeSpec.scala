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

object RealtimeAPIMock extends PlurkOAuth(null) with MockOAuth {

  val userChannelJSON = JsonParser.parse("""{
   "comet_server": "http://comet03.plurk.com/comet/1235515351741/?channel=generic&offset=0",
   "channel_name": "generic-4-f733d8522327edf87b4d1651e6395a6cca0807a0"
  }""")

  val eventsJSON = JsonParser.parse("""{
    "new_offset":7,
    "data":[{
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
    },{
      "replurkers":[],
      "responses_seen":0,
      "qualifier":"wants",
      "replurkers_count":0,
      "replurker_id":null,
      "response_count":0,
      "anonymous":false,
      "replurkable":true,
      "limited_to":null,
      "id":1100230981,
      "favorite_count":0,
      "is_unread":0,
      "lang":"tr_ch",
      "favorers":[],
      "content_raw":"Another test.",
      "user_id":7462357,
      "plurk_type":0,
      "replurked":false,
      "favorite":false,
      "no_comments":0,
      "content":"Another test.",
      "plurk_id":1100230981,
      "posted":"Fri, 22 Feb 2013 07:21:24 GMT",
      "type":"new_plurk",
      "owner_id":7462357
    }]
  }""")

  override def sendRequest(url: String, method: Verb, 
                           params: (String, String)*): Try[JValue] = {


    def isComet = url.startsWith("http://comet")

    (url, method) match {
      case ("/APP/Realtime/getUserChannel", Verb.GET) => Success(userChannelJSON)
      case (_, Verb.GET) if isComet => Success(eventsJSON)
      case _ => Failure(throw new Exception("Not implemented"))
    }

  }

}

class RealtimeSpec extends FunSpec with ShouldMatchers {

  describe("A PlurkAPI with Realtime trait") {

    val plurkAPI = PlurkAPI.withMock(RealtimeAPIMock)

    it ("get user channel by /APP/getUserChannel correctly") {
      val channel = plurkAPI.Realtime.getUserChannel.get

      channel should be === UserChannel(
        "http://comet03.plurk.com/comet/1235515351741/?channel=generic",
        "generic-4-f733d8522327edf87b4d1651e6395a6cca0807a0",
        0
      )
    }

    it ("get realtime event by request to UserChannel correctly") {

      val channel = UserChannel("http://comet03.plurk.com/", "name", 0)
      val events = plurkAPI.Realtime.getEvents(channel).get

      events.nextChannel should be === UserChannel("http://comet03.plurk.com/", "name", 7)
      events.events(0).isRight should be === true
      events.events(1).isLeft should be === true


    }

  }
}

