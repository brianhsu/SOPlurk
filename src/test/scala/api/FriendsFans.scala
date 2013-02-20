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

  val getFollowingResponse = JsonParser.parse("""[
    {
      "verified_account": true,
      "uid": 5530231,
      "full_name": "\u57ce\u9580\u57ce\u9580\u776b\u6bdb\u7cd5",
      "name_color": null,
      "timezone": null,
      "id": 5530231,
      "display_name": "\u8df3\u6d1e\u738b\uff0e\u8cca\u6bdb\u5b50",
      "date_of_birth": "Mon, 14 Jul 1986 00:01:00 GMT",
      "location": "Taipei, Taiwan",
      "recruited": 175,
      "bday_privacy": 2,
      "avatar": 10,
      "default_lang": "tr_ch",
      "relationship": "single",
      "dateformat": 0,
      "has_profile_image": 1,
      "email_confirmed": true,
      "settings": true,
      "nick_name": "johnnydoki",
      "gender": 1,
      "karma": 96.07,
      "following": true
    },
    {
      "verified_account": false,
      "uid": 5663569,
      "full_name": "Kyoko ",
      "name_color": null,
      "timezone": null,
      "id": 5663569,
      "display_name": "Kyoko",
      "date_of_birth": "Sat, 22 Dec 1990 00:01:00 GMT",
      "location": "\u53f0\u5317, Taiwan",
      "recruited": 2,
      "bday_privacy": 2,
      "avatar": 38,
      "default_lang": "tr_ch",
      "relationship": "not_saying",
      "dateformat": 0,
      "has_profile_image": 1,
      "email_confirmed": true,
      "settings": true,
      "nick_name": "kyoko99551",
      "gender": 0,
      "karma": 100,
      "following": true
    },
    {
      "verified_account": false,
      "uid": 8290019,
      "full_name": "Yume \u5922\u5922",
      "name_color": "0A9C17",
      "timezone": null,
      "id": 8290019,
      "display_name": "YUME",
      "date_of_birth": "Fri, 14 Jun 1996 00:01:00 GMT",
      "location": "Tapei, Taiwan",
      "recruited": 0,
      "bday_privacy": 2,
      "avatar": 54,
      "default_lang": "tr_ch",
      "relationship": "not_saying",
      "dateformat": 0,
      "has_profile_image": 1,
      "email_confirmed": true,
      "settings": true,
      "nick_name": "likerm6",
      "gender": 0,
      "karma": 100.46,
      "following": true
    },
    {
      "verified_account": false,
      "uid": 4065129,
      "full_name": "\u661f\u5bbf\u55b5",
      "name_color": "2264D6",
      "timezone": "Asia\/Taipei",
      "id": 4065129,
      "display_name": "lordmi",
      "date_of_birth": "Fri, 11 May 1973 00:01:00 GMT",
      "location": "Chungho, Taiwan",
      "recruited": 81,
      "bday_privacy": 2,
      "avatar": 2,
      "default_lang": "tr_ch",
      "relationship": "single",
      "dateformat": 0,
      "has_profile_image": 1,
      "email_confirmed": true,
      "settings": true,
      "nick_name": "lordmi",
      "gender": 1,
      "karma": 133,
      "following": true
    }
  ]""")

  val successJSON = JsonParser.parse("""{"success_text": "ok"}""")

  override def sendRequest(url: String, method: Verb, 
                           params: (String, String)*): Try[JValue] = {

    def hasFriendID(id: Long) = params.contains("friend_id" -> id.toString)
    def hasUserID(id: Long) = params.contains("user_id" -> id.toString)
    def hasOffset(offset: Int) = params.contains("offset" -> offset.toString)
    def hasLimit(limit: Int) = params.contains("limit" -> limit.toString)

    val GetFriends = "/APP/FriendsFans/getFriendsByOffset"
    val GetFans    = "/APP/FriendsFans/getFansByOffset"
    val GetFollowing = "/APP/FriendsFans/getFollowingByOffset"
    val BecomeFriend = "/APP/FriendsFans/becomeFriend"
    val RemoveAsFriend = "/APP/FriendsFans/removeAsFriend"
    val BecomeFan = "/APP/FriendsFans/becomeFan"

    (url, method) match {

      case (GetFriends, Verb.GET) if hasUserID(123L) && hasOffset(1) && hasLimit(3) => 
          Success(getFriendsResponse)

      case (GetFans, Verb.GET) if hasUserID(123L) && hasOffset(1) && hasLimit(3) => 
          Success(getFansResponse)

      case (GetFollowing, Verb.GET) if hasOffset(2) && hasLimit(4) => 
          Success(getFollowingResponse)

      case (BecomeFriend, Verb.POST) if hasFriendID(3456L) => Success(successJSON)
      case (RemoveAsFriend, Verb.POST) if hasFriendID(7890L) => Success(successJSON)
      case (BecomeFan, Verb.POST) if hasFriendID(1234L) => Success(successJSON)

      case _ => 
        Failure(throw new Exception("Not implemented"))
    }

  }

}

class FriendsFansSpec extends FunSpec with ShouldMatchers {

  describe("A PlurkAPI with FriendsFans trait") {

    val plurkAPI = PlurkAPI.withMock(FriendsFansAPIMock)

    it ("get friends of user by /APP/FriendsFans/getFriendsByOffset correctly") {

      val friends = plurkAPI.FriendsFans.getFriendsByOffset(123L, 3, Some(1)).get

      friends.size should be === 2
      friends.map(_.basicInfo.id) should be === List(8120302L, 3779288L)
    }

    it ("get fans of user by /APP/FriendsFans/getFansByOffset correctly") {

      val fans = plurkAPI.FriendsFans.getFansByOffset(123L, 3, Some(1)).get

      fans.size should be === 3
      fans.map(_.basicInfo.id) should be === List(4948413L, 6960804L, 4138952L)
    }

    it ("get following of user by /APP/FriendsFans/getFollowingByOffset correctly") {

      val following = plurkAPI.FriendsFans.getFollowingByOffset(4, Some(2)).get

      following.size should be === 4
      following.map(_.basicInfo.id) should be === List(5530231L, 5663569L, 8290019L, 4065129L)
    }

    it ("become friends with user by /APP/FriendsFans/becomeFriend correctly") {

      val isOK = plurkAPI.FriendsFans.becomeFriend(3456L).get
      isOK should be === true
    }

    it ("remove friend by /APP/FriendsFans/removeAsFriend correctly") {

      val isOK = plurkAPI.FriendsFans.removeAsFriend(7890L).get
      isOK should be === true
    }

  }
}

