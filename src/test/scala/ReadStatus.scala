package org.bone.splurk2.test.model

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

import org.bone.splurk2.exceptions._
import org.bone.splurk2.model._
import org.bone.splurk2.model.ReadStatus._

class ReadStatusSpec extends FunSpec with ShouldMatchers {

  describe("A ReadStatus") {

    it ("should have correct status code") {
      Read.code should be === 0
      Unread.code should be === 1
      Muted.code should be === 2
    }

    it ("should able to prase status code to case object") {
      ReadStatus(0) should be === Read
      ReadStatus(1) should be === Unread
      ReadStatus(2) should be === Muted
    }

    it ("should throw NoSuchReadStatusException exception when no such code") {
      val exception = intercept[NoSuchReadStatusException] {
        ReadStatus(7)
      }
      exception.code should be === 7
    }
  }
}

