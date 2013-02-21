package org.bone.soplurk.model

import org.bone.soplurk.constant._
import org.bone.soplurk.exceptions._
import org.bone.soplurk.model._

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

import java.util.Date
import net.liftweb.json._

class UserSpec extends FunSpec with ShouldMatchers {

  import UserSpec._

  describe("A User") {

    it ("should able to parse plurk's JSON data of a user has set name color") {
      val correctUserInfo = User(
        id = 8290019L,
        nickname = "User1NickName",
        fullName = "User1FullName",
        displayName = "User1DisplayName",
        isVerifiedAccount = false,
        gender = Gender.Female,
        karma = 100.31,
        hasProfileImage = true,
        birthdayPrivacy = Some(BirthdayPrivacy.ShowAll),
        defaultLanguage = "tr_ch",
        avatarVersion = Some(54),
        location = Some("Tapei, Taiwan"),
        birthday = Some(new Date(834710460000L)), // 1996-06-14T00:01:00 GMT
        timezone = None,
        nameColor = Some("0A9C17")
      )

      User(hasNameColor) should be === correctUserInfo
    }

    it ("should able to parse plurk's JSON data of a user has no name color") {
      val correctUserInfo = User(
        id = 4460064L,
        nickname = "User2Nick",
        fullName = "User2Full",
        displayName = "User2Display",
        isVerifiedAccount = false,
        gender = Gender.Female,
        karma = 114.51,
        hasProfileImage = true,
        birthdayPrivacy = Some(BirthdayPrivacy.ShowAll),
        defaultLanguage = "tr_ch",
        avatarVersion = Some(86),
        location = Some("Fatimaid, Taiwan"),
        birthday = Some(new Date(550713660000L)), // 1996-06-14T00:01:00 GMT
        timezone = None,
        nameColor = None
      )

      User(noNameColor) should be === correctUserInfo
    }


    it ("should able to get correct avatar when user has profile image without version") {

      val userID = 8290019

      User(avatarWithoutVersion).smallAvatar  should be === s"http://avatars.plurk.com/$userID-small.gif"
      User(avatarWithoutVersion).mediumAvatar should be === s"http://avatars.plurk.com/$userID-medium.gif"
      User(avatarWithoutVersion).bigAvatar    should be === s"http://avatars.plurk.com/$userID-big.jpg"
    }

    it ("should able to get correct avatar when user has profile image with version") {

      val userID = 4460064
      val avatarVersion = 86

      User(avatarWithVersion).smallAvatar  should be === s"http://avatars.plurk.com/$userID-small$avatarVersion.gif"
      User(avatarWithVersion).mediumAvatar should be === s"http://avatars.plurk.com/$userID-medium$avatarVersion.gif"
      User(avatarWithVersion).bigAvatar    should be === s"http://avatars.plurk.com/$userID-big$avatarVersion.jpg"
    }

    it ("should able to get correct avatar when user has no profile image") {

      User(noAvatar).smallAvatar  should be === "http://www.plurk.com/static/default_small.gif"
      User(noAvatar).mediumAvatar should be === "http://www.plurk.com/static/default_medium.gif"
      User(noAvatar).bigAvatar    should be === "http://www.plurk.com/static/default_big.gif"

    }


  }
}

object UserSpec {

  val hasNameColor = JsonParser.parse("""{
    "verified_account": false,
    "default_lang": "tr_ch",
    "display_name": "User1DisplayName",
    "dateformat": 0,
    "nick_name": "User1NickName",
    "has_profile_image": 1,
    "location": "Tapei, Taiwan",
    "bday_privacy": 2,
    "date_of_birth": "Fri, 14 Jun 1996 00:01:00 GMT",
    "karma": 100.31,
    "full_name": "User1FullName",
    "gender": 0,
    "name_color": "0A9C17",
    "timezone": null,
    "id": 8290019,
    "avatar": 54
  }""")

  val noNameColor = JsonParser.parse("""{
    "verified_account": false,
    "default_lang": "tr_ch",
    "display_name": "User2Display",
    "dateformat": 0,
    "nick_name": "User2Nick",
    "has_profile_image": 1,
    "location": "Fatimaid, Taiwan",
    "bday_privacy": 2,
    "date_of_birth": "Mon, 15 Jun 1987 00:01:00 GMT",
    "karma": 114.51,
    "full_name": "User2Full",
    "gender": 0,
    "name_color": null,
    "timezone": null,
    "id": 4460064,
    "avatar": 86
  }""")

  val avatarWithoutVersion = JsonParser.parse("""{
    "verified_account": false,
    "default_lang": "tr_ch",
    "display_name": "User1Display",
    "dateformat": 0,
    "nick_name": "User1Nick",
    "has_profile_image": 1,
    "location": "Tapei, Taiwan",
    "bday_privacy": 2,
    "date_of_birth": "Fri, 14 Jun 1996 00:01:00 GMT",
    "karma": 100.31,
    "full_name": "User1Full",
    "gender": 0,
    "name_color": "0A9C17",
    "timezone": null,
    "id": 8290019,
  }""")

  val avatarWithVersion = JsonParser.parse("""{
    "verified_account": false,
    "default_lang": "tr_ch",
    "display_name": "User2Display",
    "dateformat": 0,
    "nick_name": "User2Nick",
    "has_profile_image": 1,
    "location": "Fatimaid, Taiwan",
    "bday_privacy": 2,
    "date_of_birth": "Mon, 15 Jun 1987 00:01:00 GMT",
    "karma": 114.51,
    "full_name": "User2Full",
    "gender": 0,
    "name_color": null,
    "timezone": null,
    "id": 4460064,
    "avatar": 86
  }""")

  val noAvatar = JsonParser.parse("""{
    "verified_account": false,
    "default_lang": "tr_ch",
    "display_name": "User2Display",
    "dateformat": 0,
    "nick_name": "User2Nick",
    "has_profile_image": 0,
    "location": "Fatimaid, Taiwan",
    "bday_privacy": 2,
    "date_of_birth": "Mon, 15 Jun 1987 00:01:00 GMT",
    "karma": 114.51,
    "full_name": "User2Full",
    "gender": 0,
    "name_color": null,
    "timezone": null,
    "id": 4460064,
  }""")


}


