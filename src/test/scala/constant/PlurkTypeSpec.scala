package org.bone.soplurk.constant

import org.scalatest.FunSpec
import org.scalatest.Matchers

import org.bone.soplurk.constant._
import org.bone.soplurk.constant.PlurkType._
import org.bone.soplurk.exceptions._
import org.bone.soplurk.model._

class PlurkTypeSpec extends FunSpec with Matchers {

  describe("A PlurkType") {

    it ("should have correct type code") {
      Public.code shouldBe 0
      Private.code shouldBe 1
      PublicResponded.code shouldBe 2
      PrivateResponded.code shouldBe 3
      Anonymous.code shouldBe 4
      AnonymousResponded.code shouldBe 6

    }

    it ("should able to prase status code to case object") {
      PlurkType(0) shouldBe Public
      PlurkType(1) shouldBe Private
      PlurkType(2) shouldBe PublicResponded
      PlurkType(3) shouldBe PrivateResponded
      PlurkType(4) shouldBe Anonymous
      PlurkType(6) shouldBe AnonymousResponded
    }

    it ("should get PlurkType(code) object when occurs unknown code") {
      PlurkType(7).code shouldBe 7
    }
  }
}

