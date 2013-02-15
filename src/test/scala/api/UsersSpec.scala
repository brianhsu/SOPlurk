package org.bone.splurk2.api

import org.bone.splurk2.model._
import org.bone.splurk2.oauth.PlurkOAuth
import org.bone.splurk2.oauth.MockOAuth

import org.scribe.model.Verb

import net.liftweb.json.JsonAST._
import net.liftweb.json.JsonParser

import scala.util.{Try, Success, Failure}

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.PrivateMethodTester 

object UserAPIMock extends PlurkOAuth(null) with MockOAuth {


  val CurrUser = "http://www.plurk.com/APP/Users/currUser"
  val Update = "http://www.plurk.com/APP/Users/update"
  val KarmaStats = "http://www.plurk.com/APP/Users/getKarmaStats"

  val currUserResponse = JsonParser.parse("""{
    "verified_account": false,
    "page_title": "PageTitle",
    "full_name": "BrianHsu",
    "name_color": null,
    "timezone": "Asia/Taipei",
    "id": 1367985,
    "display_name": "DisplayName",
    "date_of_birth": "Tue, 02 Jan 1990 00:01:00 GMT",
    "location": "Taipei, Taiwan",
    "recruited": 15,
    "bday_privacy": 2,
    "avatar": 0,
    "default_lang": "tr_ch",
    "relationship": "single",
    "dateformat": 0,
    "has_profile_image": 1,
    "about": "UsersAbout",
    "nick_name": "brianhsu",
    "gender": 1,
    "karma": 121.02
  }""")

  val updateResponse = JsonParser.parse("""{
    "success_text": "ok"
  }""")

  val karmaStatsResponse = JsonParser.parse("""{
    "karma_fall_reason": "",
    "current_karma": 121.02,
    "karma_graph": "http://chart.googleapis.com/chart",
    "karma_trend": [
        "1360080759-120.75",
        "1360887032-121.02",
        "1360915735-121.02"
    ]
  }""")

  override def sendRequest(url: String, method: Verb, 
                           params: (String, String)*): Try[JValue] = {

    (url, method) match {
      case (CurrUser, Verb.GET)   => Success(currUserResponse)
      case (Update, Verb.POST)    => Success(updateResponse)
      case (KarmaStats, Verb.GET) => Success(karmaStatsResponse)
      case _ => Failure(throw new Exception("Not implemented"))
    }

  }

}

class UsersSpec extends FunSpec with ShouldMatchers {

  describe("A PlurkAPI with Users trait") {

    val plurkAPI = PlurkAPI.withMock(UserAPIMock)

    it ("call /APP/Users/currUser correctly") {

      val Success((userInfo, pageTitle, about)) = plurkAPI.Users.currUser

      userInfo.basicInfo.id should be === 1367985L
      userInfo.relationship should be === Relationship.Single
      userInfo.recruited should be === 15
      about should be === "UsersAbout"
      pageTitle should be === "PageTitle"
    }

    it ("call /APP/Users/update correctly") {

      val Success(isOK) = plurkAPI.Users.update(fullName = Some("NewFullName"))

      isOK should be === true
    }

    it ("call /APP/Users/getKarmaStats correctly") {

      val correctStats = new KarmaStats(
        current = 121.02,
        graphURL = "http://chart.googleapis.com/chart",
        fallReason = None,
        trend = List("1360080759-120.75", "1360887032-121.02", "1360915735-121.02")
      )

      val Success(karmaStats) = plurkAPI.Users.getKarmaStats
      karmaStats should be === correctStats

    }

  }
}

