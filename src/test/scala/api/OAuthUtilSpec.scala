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

object OAuthUtilAPIMock extends PlurkOAuth(null) with MockOAuth {

  val checkTokenJSON = JsonParser.parse("""{
    "issued": "Fri, 22 Feb 2013 05:11:23 GMT",
    "user_id": 1367985,
    "app_id": 8475,
    "deviceid": ""
  }""")

  val expireTokenJSON = JsonParser.parse("""{
  }""")

  override def sendRequest(url: String, method: Verb, 
                           params: (String, String)*): Try[JValue] = {


    (url, method) match {

      case ("/APP/checkToken", Verb.GET) => Success(checkTokenJSON)
      case ("/APP/expireToken", Verb.GET) => Success(expireTokenJSON)
      case _ => Failure(throw new Exception("Not implemented"))
    }

  }

}

class OAuthUtilSpec extends FunSpec with ShouldMatchers {

  describe("A PlurkAPI with OAuthUtil trait") {

    val plurkAPI = PlurkAPI.withMock(OAuthUtilAPIMock)

    it ("get token info by /APP/checkToken correctly") {
      val tokenInfo = plurkAPI.OAuthUtils.checkToken.get
      val correctToken = TokenInfo(8475L, 1367985L, new Date(1361509883000L), "")
      tokenInfo should be === correctToken
    }

    it ("expire token by /APP/checkToken correctly") {
      pending
    }

    it ("get current time of Plurk servers by /APP/checkTime correctly") {
      pending
    }

    it ("get echo of OAuth request by /APP/echo correctly") {
      pending
    }

  }
}

