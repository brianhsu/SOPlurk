package org.bone.soplurk.model

import org.bone.soplurk.exceptions._
import org.bone.soplurk.model._
import org.bone.soplurk.model.PlurkType._

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

import java.util.Date
import net.liftweb.json._

class ResponseSpec extends FunSpec with ShouldMatchers {

  import ResponseSpec._

  describe("A Response") {

    it ("should able to parse plurk's JSON data") {

      val correctResponse = new Response(
        userID = 1367985L, plurkID = 1099531474L, id = 5385889041L,
        content = "ResponseContent", contentRaw = "ResponseContentRaw",
        qualifier = Qualifier.Says,
        posted = new Date(1361325370000L),
        language = "en"
      )

      Response(responseJSON) should be === correctResponse

    }
  }
}

object ResponseSpec {

  val responseJSON = JsonParser.parse("""{
    "lang": "en",
    "content_raw": "ResponseContentRaw",
    "user_id": 1367985,
    "qualifier": "says",
    "plurk_id": 1099531474,
    "content": "ResponseContent",
    "id": 5385889041,
    "posted": "Wed, 20 Feb 2013 01:56:10 GMT"
  }""")

}


