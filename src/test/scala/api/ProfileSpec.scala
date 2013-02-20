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

object ProfileAPIMock extends PlurkOAuth(null) with MockOAuth {


  val ownProfileResponse = JsonParser.parse("""{
    "fans_count": 280,
    "friends_count": 46,
    "unread_count": 3
    "alerts_count": 0,
    "privacy": "world",
    "has_read_permission": true,
    "plurks_users": {
        "3986856": {
            "verified_account": false,
            "default_lang": "tr_ch",
            "display_name": "九命嵐 CWT33在C05",
            "dateformat": 0,
            "nick_name": "arashicat",
            "has_profile_image": 1,
            "location": "Taipei, Taiwan",
            "bday_privacy": 2,
            "date_of_birth": "Sat, 23 Jun 1984 00:01:00 GMT",
            "karma": 112.4,
            "full_name": "九命嵐 cat",
            "gender": 0,
            "name_color": "2264D6",
            "timezone": null,
            "id": 3986856,
            "avatar": 32
        },
        "5663569": {
            "verified_account": false,
            "default_lang": "tr_ch",
            "display_name": "Kyoko",
            "dateformat": 0,
            "nick_name": "kyoko99551",
            "has_profile_image": 1,
            "location": "台北, Taiwan",
            "bday_privacy": 2,
            "date_of_birth": "Sat, 22 Dec 1990 00:01:00 GMT",
            "karma": 100,
            "full_name": "Kyoko ",
            "gender": 0,
            "name_color": null,
            "timezone": null,
            "id": 5663569,
            "avatar": 38
        }
    },
    "user_info": {
        "verified_account": false,
        "default_lang": "tr_ch",
        "display_name": "墳墓（Brian Hsu）",
        "uid": 1367985,
        "relationship": "single",
        "dateformat": 0,
        "nick_name": "brianhsu",
        "has_profile_image": 1,
        "location": "Taipei, Taiwan",
        "bday_privacy": 2,
        "about": "AboutContent",
        "date_of_birth": "Tue, 02 Jan 1990 00:01:00 GMT",
        "karma": 121.05,
        "full_name": "BrianHsu",
        "gender": 1,
        "name_color": null,
        "timezone": "Asia/Taipei",
        "recruited": 15,
        "id": 1367985,
        "email_confirmed": true,
        "avatar": 0
    },
    "plurks": [
        {
            "replurkers_count": 0,
            "replurkable": false,
            "favorite_count": 0,
            "is_unread": 0,
            "content": "電話費果然如預期般大爆炸!!",
            "user_id": 1367985,
            "plurk_type": 0,
            "qualifier_translated": "說",
            "replurked": false,
            "favorers": [],
            "replurker_id": null,
            "owner_id": 4147596,
            "responses_seen": 0,
            "qualifier": "says",
            "plurk_id": 1098703292,
            "response_count": 2,
            "limited_to": null,
            "no_comments": 0,
            "posted": "Sun, 17 Feb 2013 11:58:45 GMT",
            "lang": "tr_ch",
            "content_raw": "電話費果然如預期般大爆炸!!",
            "replurkers": [],
            "favorite": false
        },
        {
            "responses_seen": 0,
            "qualifier": "says",
            "replurkers": [],
            "plurk_id": 1098692698,
            "response_count": 1,
            "replurkers_count": 0,
            "replurkable": true,
            "limited_to": null,
            "no_comments": 0,
            "favorite_count": 0,
            "is_unread": 0,
            "lang": "tr_ch",
            "favorers": [],
            "content_raw": "剛剛才發現，原來 Simon Says 搶銀行那集是終極警探三，然後我沒看過一和二。XDD",
            "user_id": 1367985,
            "plurk_type": 0,
            "qualifier_translated": "說",
            "replurked": false,
            "favorite": false,
            "content": "剛剛才發現，原來 Simon Says 搶銀行那集是終極警探三，然後我沒看過一和二。XDD",
            "replurker_id": null,
            "posted": "Sun, 17 Feb 2013 11:18:44 GMT",
            "owner_id": 1367985
        },
        {
            "replurkers_count": 0,
            "replurkable": false,
            "favorite_count": 0,
            "is_unread": 0,
            "content": "雖然拍到不多還是來速一下好了",
            "user_id": 1367985,
            "plurk_type": 0,
            "qualifier_translated": "說",
            "replurked": false,
            "favorers": [],
            "replurker_id": null,
            "owner_id": 6888088,
            "responses_seen": 0,
            "qualifier": "says",
            "plurk_id": 1098501043,
            "response_count": 9,
            "limited_to": null,
            "no_comments": 0,
            "posted": "Sat, 16 Feb 2013 16:29:37 GMT",
            "lang": "tr_ch",
            "content_raw": "雖然拍到不多還是來速一下好了",
            "replurkers": [],
            "favorite": false
        }
    ]
  }""")

  val publicProfileResponse = JsonParser.parse("""{
    "is_fan": true,
    "fans_count": 191,
    "friends_count": 190,
    "privacy": "world",
    "has_read_permission": true,
    "are_friends": false,
    "is_following": true,
    "user_info": {
        "verified_account": false,
        "default_lang": "ja",
        "display_name": "觀月麻呂",
        "uid": 4426947,
        "relationship": "not_saying",
        "dateformat": 0,
        "nick_name": "maro0126",
        "has_profile_image": 1,
        "location": "Taipei, Taiwan",
        "bday_privacy": 0,
        "about": "About",
        "date_of_birth": null,
        "karma": 100.22,
        "full_name": "觀月麻呂",
        "gender": 0,
        "name_color": "E41227",
        "timezone": null,
        "recruited": 23,
        "id": 4426947,
        "email_confirmed": true,
        "avatar": 26
    },
    "plurks": [
        {
            "replurkers_count": 0,
            "replurkable": true,
            "favorite_count": 0,
            "favorers": [],
            "user_id": 4426947,
            "plurk_type": 0,
            "qualifier_translated": "喜歡",
            "replurked": null,
            "content": "五月生日快樂！！*\\(^o^)/*",
            "replurker_id": null,
            "owner_id": 4426947,
            "responses_seen": 0,
            "qualifier": "likes",
            "plurk_id": 1098814172,
            "response_count": 1,
            "limited_to": null,
            "no_comments": 0,
            "posted": "Sun, 17 Feb 2013 17:08:09 GMT",
            "lang": "tr_ch",
            "content_raw": "五月生日快樂！！*\\(^o^)/*",
            "replurkers": []
        },
        {
            "replurkers_count": 0,
            "replurkable": true,
            "favorite_count": 2,
            "favorers": [
                5042068,
                5658139
            ],
            "user_id": 4426947,
            "plurk_type": 0,
            "qualifier_translated": "喜歡",
            "replurked": null,
            "content": "Content2",
            "replurker_id": null,
            "owner_id": 4426947,
            "responses_seen": 0,
            "qualifier": "likes",
            "plurk_id": 1098813971,
            "response_count": 13,
            "limited_to": null,
            "no_comments": 0,
            "posted": "Sun, 17 Feb 2013 17:07:27 GMT",
            "lang": "tr_ch",
            "content_raw": "Content2Raw",
            "replurkers": []
        },
    ]
  }""")

  val privateProfileResponse = JsonParser.parse("""{
    "is_fan": false,
    "fans_count": 1,
    "friends_count": 200,
    "privacy": "only_friends",
    "has_read_permission": false,
    "user_info": {
        "verified_account": false,
        "default_lang": "tr_ch",
        "display_name": "tttt",
        "uid": 3290730,
        "relationship": "single",
        "dateformat": 0,
        "nick_name": "Dannvix",
        "has_profile_image": 1,
        "location": "Taipei, Taiwan",
        "bday_privacy": 2,
        "about": "Users' about",
        "date_of_birth": "Wed, 25 Oct 1989 00:01:00 GMT",
        "karma": 100.1,
        "full_name": "qqqqq",
        "gender": 1,
        "name_color": null,
        "timezone": "Asia/Taipei",
        "recruited": 1,
        "id": 3290730,
        "email_confirmed": true,
        "avatar": 44
    },
    "are_friends": false,
    "is_following": false,
    "plurks": []
  }""")

  override def sendRequest(url: String, method: Verb, 
                           params: (String, String)*): Try[JValue] = {

    def isUser(nickname: String) = params contains ("user_id", nickname)

    (url, method) match {
      case ("/APP/Profile/getOwnProfile", Verb.GET) => 
        Success(ownProfileResponse)

      case ("/APP/Profile/getPublicProfile", Verb.GET) if isUser("public") => 
        Success(publicProfileResponse)

      case ("/APP/Profile/getPublicProfile", Verb.GET) if isUser("private") => 
        Success(privateProfileResponse)

      case _ => Failure(throw new Exception("Not implemented"))
    }

  }

}

class ProfileSpec extends FunSpec with ShouldMatchers {

  describe("A PlurkAPI with Profile trait") {

    val plurkAPI = PlurkAPI.withMock(ProfileAPIMock)

    it ("call getOwnProfile correctly") {
      val profile = plurkAPI.Profile.getOwnProfile.get

      profile.fansCount should be === 280
      profile.friendsCount should be === 46
      profile.unreadCount should be === 3
      profile.alertsCount should be === 0
      profile.privacy should be === TimelinePrivacy.World
      profile.userInfo.basicInfo.id should be === 1367985

      val Timeline(users, plurks) = profile.timeline
      users.size should be === 2
      plurks.size should be === 3
    }

    it ("call getPublicProfile to a public timeline") {
      val profile = plurkAPI.Profile.getPublicProfile("public").get
      
      profile.userInfo.basicInfo.id should be === 4426947
      profile.userInfo.basicInfo.defaultLanguage should be === "ja"
      profile.userInfo.relationship should be === Relationship.NotSaying
      profile.fansCount should be === 191
      profile.friendsCount should be === 190
      profile.privacy should be === TimelinePrivacy.World
      profile.hasReadPermission should be === true
      profile.isFan should be === Some(true)
      profile.isFollowing should be === Some(true)
      profile.areFriends should be === Some(false)
      profile.plurks.size should be === 2
    }

    it ("call getPublicProfile to a private timeline") {

      val profile = plurkAPI.Profile.getPublicProfile("private").get
      
      profile.userInfo.basicInfo.id should be === 3290730
      profile.fansCount should be === 1
      profile.friendsCount should be === 200
      profile.privacy should be === TimelinePrivacy.OnlyFriends
      profile.hasReadPermission should be === false
      profile.isFan should be === Some(false)
      profile.isFollowing should be === Some(false)
      profile.areFriends should be === Some(false)
      profile.plurks should be === Nil
    }

  }
}

