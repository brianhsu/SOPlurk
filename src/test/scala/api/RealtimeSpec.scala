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

  override def sendRequest(url: String, method: Verb, 
                           params: (String, String)*): Try[JValue] = {


    (url, method) match {
      case ("/APP/Realtime/getUserChannel", Verb.GET) => Success(userChannelJSON)
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

  }
}

