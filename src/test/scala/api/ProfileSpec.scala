package org.bone.soplurk.api

import org.bone.soplurk.model._
import org.bone.soplurk.oauth.PlurkOAuth
import org.bone.soplurk.oauth.MockOAuth

import org.scribe.model.Verb

import net.liftweb.json.JsonAST._
import net.liftweb.json.JsonParser

import scala.util.{Try, Success, Failure}

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.PrivateMethodTester 

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
        "about": "不要問我為什麼我叫墳墓，因為我自己也不知道！\r\n\r\n生命：\r\n - http://0rz.tw/XDL7N  (我的部落格)\r\n - http://0rz.tw/uGiqD (充滿宅味的相簿)\r\n\r\n\r\n興趣：\r\n - 動漫、遊戲、輕小說、相聲、舞台劇、寫程式、上台演講（無誤）、女僕喫茶萬歲！\r\n\r\n現職：\r\n - 中研院http://0rz.tw/jOzE5 (數位典藏計劃)打工仔。\r\n\r\n廣告：CC Collection 要出第四集了！",
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

  override def sendRequest(url: String, method: Verb, 
                           params: (String, String)*): Try[JValue] = {

    (url, method) match {
      case ("/APP/Profile/getOwnProfile", Verb.GET) => Success(ownProfileResponse)

      case _ => Failure(throw new Exception("Not implemented"))
    }

  }

}

class ProfileSpec extends FunSpec with ShouldMatchers {

  describe("A PlurkAPI with Profile trait") {

    val plurkAPI = PlurkAPI.withMock(ProfileAPIMock)

    it ("call /APP/Profile/getOwnProfile correctly") {
      val profile = plurkAPI.Profile.getOwnProfile.get

      profile.fansCount should be === 280
      profile.friendsCount should be === 46
      profile.unreadCount should be === 3
      profile.alertsCount should be === 0
      profile.privacy should be === TimelinePrivacy.World
      profile.userInfo.basicInfo.id should be === 1367985

      val (users, plurks) = profile.timeline
      users.size should be === 2
      plurks.size should be === 3
    }

  }
}

