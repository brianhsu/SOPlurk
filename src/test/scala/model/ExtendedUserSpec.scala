package org.bone.splurk2.model

import org.bone.splurk2.exceptions._
import org.bone.splurk2.model._
import org.bone.splurk2.model.PlurkType._

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

import java.util.Date
import net.liftweb.json._

class ExtendedUserSpec extends FunSpec with ShouldMatchers {

  import ExtendedUserSpec._

  describe("A ExtendedUser") {

    it ("should able to parse plurk's JSON data") {
      val basicInfo = new User (
        id = 3906924L,
        nickname = "UserNickName",
        fullName = "UserFullName",
        displayName = "UserDisplayName",
        isVerifiedAccount = false,
        gender = Gender.Female,
        karma = 82.37,
        hasProfileImage = true,
        birthdayPrivacy = BirthdayPrivacy.ShowAll,
        defaultLanguage = "tr_ch",
        avatarVersion = Some(40),
        location = Some("Taipei, Taiwan"),
        birthday = Some(new Date(878947260000L)),  // 1997-11-08T00:01:00 GMT
        timezone = None,
        nameColor = None
      )

      ExtendedUser(userJSON) should be === ExtendedUser(basicInfo, Relationship.NotSaying, 32)
    }
  }
}

object ExtendedUserSpec {

  val userJSON = JsonParser.parse("""{
    "verified_account": false,
    "default_lang": "tr_ch",
    "display_name": "UserDisplayName",
    "uid": 3906924,
    "relationship": "not_saying",
    "dateformat": 0,
    "nick_name": "UserNickName",
    "has_profile_image": 1,
    "location": "Taipei, Taiwan",
    "bday_privacy": 2,
    "about": "AboutContent",
    "date_of_birth": "Sat, 08 Nov 1997 00:01:00 GMT",
    "karma": 82.37,
    "full_name": "UserFullName",
    "gender": 0,
    "name_color": null,
    "timezone": null,
    "recruited": 32,
    "id": 3906924,
    "email_confirmed": true,
    "avatar": 40
  }""")

}


