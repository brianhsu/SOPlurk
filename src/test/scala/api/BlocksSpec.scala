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

object BlocksAPIMock extends PlurkOAuth(null) with MockOAuth {

  val getBlocksResponse = JsonParser.parse("""{
    "total": 43,
    "users": [
        {
            "verified_account": false,
            "default_lang": "tr_ch",
            "display_name": "AAAA",
            "dateformat": 0,
            "nick_name": "autowretch",
            "has_profile_image": 1,
            "location": "BBBB",
            "bday_privacy": 0,
            "date_of_birth": null,
            "karma": 0,
            "full_name": "CCC",
            "gender": 1,
            "timezone": null,
            "id": 6704708,
            "avatar": 2
        },
        {
            "verified_account": false,
            "default_lang": "tr_ch",
            "display_name": "BBBB",
            "dateformat": 0,
            "nick_name": "557557",
            "has_profile_image": 1,
            "location": "",
            "bday_privacy": 0,
            "date_of_birth": null,
            "karma": 0.45,
            "full_name": "DDDDDD",
            "gender": 0,
            "timezone": "Asia/Taipei",
            "id": 3711781,
            "avatar": 22
        }
    ]
  }""")

  val successJSON = JsonParser.parse("""{"success_text": "ok"}""")

  override def sendRequest(url: String, method: Verb, 
                           params: (String, String)*): Try[JValue] = {


    def hasOffset(offset: Int) = params.contains("offset" -> offset.toString)
    def hasUserID(id: Long) = params.contains("user_id" -> id.toString)

    (url, method) match {

      case ("/APP/Blocks/get", Verb.GET) if hasOffset(3) => Success(getBlocksResponse)
      case ("/APP/Blocks/block", Verb.POST) if hasUserID(1234L) => Success(successJSON)
      case ("/APP/Blocks/unblock", Verb.POST) if hasUserID(5678) => Success(successJSON)
      case _ => Failure(throw new Exception("Not implemented"))
    }

  }

}

class BlocksSpec extends FunSpec with Matchers {

  describe("A PlurkAPI with Blocks trait") {

    val plurkAPI = PlurkAPI.withMock(BlocksAPIMock)

    it ("get block list by /APP/Blocks/get correctly") {

      val Success((total, users)) = plurkAPI.Blocks.get(offset = 3)

      total shouldBe 43
      users.size shouldBe 2
    }

    it ("block a user by /APP/Blocks/block correctly") {
      val isOK = plurkAPI.Blocks.block(1234L).get
      isOK shouldBe true
    }

    it ("unblock a user by /APP/Blocks/block correctly") {
      val isOK = plurkAPI.Blocks.unblock(5678L).get
      isOK shouldBe true
    }

  }
}

