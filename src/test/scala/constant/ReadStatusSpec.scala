package org.bone.soplurk.constant

import org.scalatest.FunSpec
import org.scalatest.Matchers

import org.bone.soplurk.constant._
import org.bone.soplurk.constant.ReadStatus._
import org.bone.soplurk.exceptions._
import org.bone.soplurk.model._

class ReadStatusSpec extends FunSpec with Matchers {

  describe("A ReadStatus") {

    it ("should have correct status code") {
      Read.code shouldBe 0
      Unread.code shouldBe 1
      Muted.code shouldBe 2
    }

    it ("should able to prase status code to case object") {
      ReadStatus(0) shouldBe Read
      ReadStatus(1) shouldBe Unread
      ReadStatus(2) shouldBe Muted
    }

    it ("should throw NoSuchReadStatusException exception when no such code") {
      val exception = intercept[NoSuchReadStatusException] {
        ReadStatus(7)
      }
      exception.code shouldBe 7
    }
  }
}

