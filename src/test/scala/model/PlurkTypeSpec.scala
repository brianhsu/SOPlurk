package org.bone.soplurk.model

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

import org.bone.soplurk.exceptions._
import org.bone.soplurk.model._
import org.bone.soplurk.model.PlurkType._

class PlurkTypeSpec extends FunSpec with ShouldMatchers {

  describe("A PlurkType") {

    it ("should have correct type code") {
      Public.code should be === 0
      Private.code should be === 1
      PublicResponded.code should be === 2
      PrivateResponded.code should be === 3
      Anonymous.code should be === 4
      AnonymousResponded.code should be === 6

    }

    it ("should able to prase status code to case object") {
      PlurkType(0) should be === Public
      PlurkType(1) should be === Private
      PlurkType(2) should be === PublicResponded
      PlurkType(3) should be === PrivateResponded
      PlurkType(4) should be === Anonymous
      PlurkType(6) should be === AnonymousResponded
    }

    it ("should get PlurkType(code) object when occurs unknown code") {
      PlurkType(7).code should be === 7
    }
  }
}

