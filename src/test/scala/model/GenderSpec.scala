package org.bone.soplurk.model

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

import org.bone.soplurk.exceptions._
import org.bone.soplurk.model._
import org.bone.soplurk.model.Gender._

class GenderSpec extends FunSpec with ShouldMatchers {

  describe("A BrithdayPrivacy") {

    it ("should have correct type code") {
      Female.code should be === 0
      Male.code should be === 1
      NotStating.code should be === 2
    }

    it ("should able to prase status code to case object") {
      Gender(0) should be === Female
      Gender(1) should be === Male
      Gender(2) should be === NotStating
    }

    it ("should throw NoSuchGenderException at unknown code") {
      val exception = intercept[NoSuchGenderException] {
        Gender(7)
      }
      exception.code should be === 7
    }
  }
}

