package org.bone.soplurk.constant

import org.scalatest.FunSpec
import org.scalatest.Matchers

import org.bone.soplurk.constant._
import org.bone.soplurk.constant.BirthdayPrivacy._
import org.bone.soplurk.exceptions._
import org.bone.soplurk.model._

class BirthdayPrivacySpec extends FunSpec with Matchers {

  describe("A BrithdayPrivacy") {

    it ("should have correct type code") {
      HideAll.code shouldBe 0
      HideYear.code shouldBe 1
      ShowAll.code shouldBe 2
    }

    it ("should able to prase status code to case object") {
      BirthdayPrivacy(0) shouldBe HideAll
      BirthdayPrivacy(1) shouldBe HideYear
      BirthdayPrivacy(2) shouldBe ShowAll
    }

    it ("should throw NoSuchBirthdayPrivacyException at unknown code") {
      val exception = intercept[NoSuchBirthdayPrivacyException] {
        BirthdayPrivacy(7)
      }
      exception.code shouldBe 7
    }
  }
}

