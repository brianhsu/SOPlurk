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

object FriendsFansAPIMock extends PlurkOAuth(null) with MockOAuth {

  val getFriendsResponse = JsonParser.parse("""[
    {
      "verified_account": false,
      "uid": 8120302,
      "following_im": 1,
      "full_name": "0Mission0",
      "name_color": null,
      "timezone": null,
      "id": 8120302,
      "display_name": "\u54aa\u54aa\u734e",
      "date_of_birth": "Tue, 14 Feb 1984 00:01:00 GMT",
      "location": "Hsinchu, Taiwan",
      "recruited": 1,
      "bday_privacy": 2,
      "avatar": 2,
      "default_lang": "tr_ch",
      "relationship": "single",
      "dateformat": 0,
      "has_profile_image": 1,
      "following_tl": 1,
      "email_confirmed": true,
      "settings": true,
      "nick_name": "0Mission0",
      "gender": 1,
      "karma": 96.01,
      "following": true
    },
    {
      "verified_account": false,
      "default_lang": "tr_ch",
      "uid": 3779288,
      "relationship": "single",
      "dateformat": 0,
      "has_profile_image": 1,
      "following_tl": 1,
      "email_confirmed": true,
      "full_name": "\u742e\u4ec1 \u738b",
      "timezone": null,
      "id": 3779288,
      "display_name": "abhaac",
      "following_im": 1,
      "settings": true,
      "nick_name": "abhaac",
      "gender": 1,
      "avatar": 2,
      "date_of_birth": "Tue, 03 Jul 1984 00:01:00 GMT",
      "location": "PingTung city, Taiwan",
      "following": true,
      "recruited": 0,
      "bday_privacy": 2,
      "karma": 0
    }
  ]""")

  val getFansResponse = JsonParser.parse("""[
    {
      "verified_account": false,
      "default_lang": "tr_ch",
      "display_name": "\u695a\u5eb7\u3000\u8c6b",
      "uid": 4948413,
      "relationship": "complicated",
      "dateformat": 0,
      "nick_name": "036907854007",
      "has_profile_image": 1,
      "settings": false,
      "location": "Taiwan",
      "bday_privacy": 2,
      "date_of_birth": "Thu, 31 Jul 1930 00:01:00 GMT",
      "karma": 14.3,
      "full_name": "M S",
      "gender": 1,
      "following": false,
      "timezone": null,
      "recruited": 6,
      "id": 4948413,
      "email_confirmed": true,
      "avatar": 6
    },
    {
      "verified_account": false,
      "default_lang": "tr_ch",
      "display_name": "\u2611 \u674e\u9234",
      "uid": 6960804,
      "relationship": "single",
      "dateformat": 0,
      "nick_name": "2412",
      "has_profile_image": 1,
      "settings": true,
      "location": "Taipei, Taiwan",
      "bday_privacy": 0,
      "date_of_birth": null,
      "karma": 0.75,
      "full_name": "2412 2412",
      "gender": 2,
      "following": false,
      "timezone": "Asia\/Taipei",
      "recruited": 1,
      "id": 6960804,
      "email_confirmed": true,
      "avatar": 4
    },
    {
      "verified_account": false,
      "default_lang": "tr_ch",
      "display_name": "\u5c0f\u905b\u905b\u2122",
      "uid": 4138952,
      "relationship": "complicated",
      "dateformat": 0,
      "nick_name": "610game",
      "has_profile_image": 1,
      "settings": true,
      "location": "Taipei, Taiwan",
      "bday_privacy": 2,
      "date_of_birth": "Thu, 10 Jun 1982 00:01:00 GMT",
      "karma": 13.61,
      "full_name": "\u5c0f\u905b\u905b",
      "gender": 0,
      "following": false,
      "timezone": "Asia\/Taipei",
      "recruited": 40,
      "id": 4138952,
      "email_confirmed": true,
      "avatar": 42
    }
  ]""")


  override def sendRequest(url: String, method: Verb, 
                           params: (String, String)*): Try[JValue] = {


    def hasUserID(id: Long) = params.contains("user_id" -> id.toString)
    def hasOffset(offset: Int) = params.contains("offset" -> offset.toString)
    def hasLimit(limit: Int) = params.contains("limit" -> limit.toString)

    val GetFriends = "/APP/FriendsFans/getFriendsByOffset"
    val GetFans    = "/APP/FriendsFans/getFansByOffset"

    (url, method) match {

      case (GetFriends, Verb.GET) if hasUserID(123L) && hasOffset(1) && hasLimit(3) => 
          Success(getFriendsResponse)

      case (GetFans, Verb.GET) if hasUserID(123L) && hasOffset(1) && hasLimit(3) => 
          Success(getFansResponse)

      case _ => 
        Failure(throw new Exception("Not implemented"))
    }

  }

}

class FriendsFansSpec extends FunSpec with ShouldMatchers {

  describe("A PlurkAPI with FriendsFans trait") {

    val plurkAPI = PlurkAPI.withMock(FriendsFansAPIMock)

    it ("get friends of user by /APP/FriendsFans/getFriendsByOffset correctly") {
      pending
    }

    it ("get fans of user by /APP/FriendsFans/getFansByOffset correctly") {

      val fans = plurkAPI.FriendsFans.getFansByOffset(123L, 3, Some(1)).get

      fans.size should be === 3
      fans.map(_.basicInfo.id) should be === List(4948413L, 6960804L, 4138952L)
    }


  }
}

