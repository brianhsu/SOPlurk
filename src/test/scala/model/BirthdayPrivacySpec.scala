package org.bone.soplurk.model

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

import org.bone.soplurk.exceptions._
import org.bone.soplurk.model._
import org.bone.soplurk.model.BirthdayPrivacy._

class BirthdayPrivacySpec extends FunSpec with ShouldMatchers {

  describe("A BrithdayPrivacy") {

    it ("should have correct type code") {
      HideAll.code should be === 0
      HideYear.code should be === 1
      ShowAll.code should be === 2
    }

    it ("should able to prase status code to case object") {
      BirthdayPrivacy(0) should be === HideAll
      BirthdayPrivacy(1) should be === HideYear
      BirthdayPrivacy(2) should be === ShowAll
    }

    it ("should throw NoSuchBirthdayPrivacyException at unknown code") {
      val exception = intercept[NoSuchBirthdayPrivacyException] {
        BirthdayPrivacy(7)
      }
      exception.code should be === 7
    }
  }
}

