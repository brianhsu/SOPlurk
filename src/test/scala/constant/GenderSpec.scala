package org.bone.soplurk.constant

import org.scalatest.FunSpec
import org.scalatest.Matchers

import org.bone.soplurk.constant._
import org.bone.soplurk.constant.Gender._
import org.bone.soplurk.exceptions._
import org.bone.soplurk.model._

class GenderSpec extends FunSpec with Matchers {

  describe("A BrithdayPrivacy") {

    it ("should have correct type code") {
      Female.code shouldBe 0
      Male.code shouldBe 1
      NotStating.code shouldBe 2
    }

    it ("should able to prase status code to case object") {
      Gender(0) shouldBe Female
      Gender(1) shouldBe Male
      Gender(2) shouldBe NotStating
    }

    it ("should throw NoSuchGenderException at unknown code") {
      val exception = intercept[NoSuchGenderException] {
        Gender(7)
      }
      exception.code shouldBe 7
    }
  }
}

