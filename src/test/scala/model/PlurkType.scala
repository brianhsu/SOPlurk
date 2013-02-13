package org.bone.splurk2.model

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

import org.bone.splurk2.exceptions._
import org.bone.splurk2.model._
import org.bone.splurk2.model.PlurkType._

class PlurkTypeSpec extends FunSpec with ShouldMatchers {

  describe("A PlurkType") {

    it ("should have correct type code") {
      PublicPlurk.code should be === 0
      PrivatePlurk.code should be === 1
      PublicResponded.code should be === 2
      PrivateResponded.code should be === 3
      AnonymousPlurk.code should be === 4
    }

    it ("should able to prase status code to case object") {
      PlurkType(0) should be === PublicPlurk
      PlurkType(1) should be === PrivatePlurk
      PlurkType(2) should be === PublicResponded
      PlurkType(3) should be === PrivateResponded
      PlurkType(4) should be === AnonymousPlurk
    }

    it ("should throw NoSuchReadStatusException exception when no such code") {
      val exception = intercept[NoSuchPlurkTypeException] {
        PlurkType(7)
      }
      exception.code should be === 7
    }
  }
}

