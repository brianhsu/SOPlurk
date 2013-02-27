package org.bone.soplurk.model


import org.bone.soplurk.exceptions._
import org.bone.soplurk.model._

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

import java.util.Date
import net.liftweb.json._

class CompletionSpec extends FunSpec with ShouldMatchers {

  import CompletionSpec._

  describe("A Completion") {

    it ("should able to parse plurk's JSON data that has display name") {
      val correctCompletion = Completion(
        nickname = "userNick",
        fullName = "userFull",
        displayName = Some("userDisplay")
      )

      Completion(withDisplayName) should be === correctCompletion
    }

    it ("should able to parse plurk's JSON data that has no display name") {
      val correctCompletion = Completion(
        nickname = "userNick",
        fullName = "userFull",
        displayName = None
      )

      Completion(withoutDisplayName) should be === correctCompletion
    }

  }
}

object CompletionSpec {

  val withDisplayName = JsonParser.parse("""{
    "nick_name": "userNick",
    "display_name": "userDisplay",
    "full_name": "userFull"
  }""")

  val withoutDisplayName = JsonParser.parse("""{
    "nick_name": "userNick",
    "full_name": "userFull"
  }""")

}


