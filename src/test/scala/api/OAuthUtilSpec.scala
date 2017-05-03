package org.bone.soplurk.api

import org.bone.soplurk.api.PlurkAPI._
import org.bone.soplurk.oauth.PlurkOAuth
import org.bone.soplurk.oauth.MockOAuth
import org.bone.soplurk.model._

import org.scalatest.FunSpec
import org.scalatest.Matchers
import org.scalatest.PrivateMethodTester 

import org.scribe.model.Verb

import net.liftweb.json.JsonAST._
import net.liftweb.json.JsonParser

import scala.util.{Try, Success, Failure}
import java.util.Date

object OAuthUtilAPIMock extends PlurkOAuth(null) with MockOAuth {

  val checkTokenJSON = JsonParser.parse("""{
    "issued": "Fri, 22 Feb 2013 05:11:23 GMT",
    "user_id": 1367985,
    "app_id": 8475,
    "deviceid": ""
  }""")

  val expireTokenJSON = JsonParser.parse("""{
    "issued": "Fri, 22 Feb 2013 05:11:23 GMT",
    "user_id": 1367986,
    "app_id": 8476,
    "deviceid": "iphone"
  }""")

  val checkTimeJSON = JsonParser.parse("""{
    "timestamp": 1361511154,
    "now": "Fri, 22 Feb 2013 05:32:34 GMT",
    "user_id": null,
    "app_id": 8477
  }""")

  val echoJSON = JsonParser.parse("""{
    "length": 8,
    "data": "testDATA"
  }""")

  override def sendRequest(url: String, method: Verb, 
                           params: (String, String)*): Try[JValue] = {


    def hasData(data: String) = params.contains("data" -> data)

    (url, method) match {

      case ("/APP/checkToken", Verb.GET) => Success(checkTokenJSON)
      case ("/APP/expireToken", Verb.POST) => Success(expireTokenJSON)
      case ("/APP/checkTime", Verb.GET) => Success(checkTimeJSON)
      case ("/APP/echo", Verb.POST) if hasData("testDATA") => Success(echoJSON)
      case _ => Failure(throw new Exception("Not implemented"))
    }

  }

}

class OAuthUtilSpec extends FunSpec with Matchers {

  describe("A PlurkAPI with OAuthUtil trait") {

    val plurkAPI = PlurkAPI.withMock(OAuthUtilAPIMock)

    it ("get token info by /APP/checkToken correctly") {
      val tokenInfo = plurkAPI.OAuthUtils.checkToken.get
      val correctToken = TokenInfo(8475L, 1367985L, new Date(1361509883000L), "")
      tokenInfo shouldBe correctToken
    }

    it ("expire token by /APP/checkToken correctly") {
      val tokenInfo = plurkAPI.OAuthUtils.expireToken.get
      val correctToken = TokenInfo(8476L, 1367986L, new Date(1361509883000L), "iphone")

      tokenInfo shouldBe correctToken
    }

    it ("get current time of Plurk servers by /APP/checkTime correctly") {
      val timeInfo = plurkAPI.OAuthUtils.checkTime.get
      val correctTime = TimeInfo(8477L, None, 1361511154L, new Date(1361511154000L))

      timeInfo shouldBe correctTime
    }

    it ("get echo of OAuth request by /APP/echo correctly") {
      val echo = plurkAPI.OAuthUtils.echo("testDATA").get
      echo shouldBe (8, "testDATA")
    }

  }
}

