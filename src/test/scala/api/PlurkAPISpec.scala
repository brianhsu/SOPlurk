package org.bone.soplurk.api

import org.scalatest.FunSpec
import org.scalatest.Matchers
import org.scalatest.PrivateMethodTester 

class PlurkAPISpec extends FunSpec with Matchers {

  describe("A PlurkAPI") {

    it ("should return Failure when calling authorize before getAuthorizationURL") {

      intercept[IllegalStateException] {

        PlurkAPI.withoutCallback("appKey", "appSecret").authorize("code").get
      }

    }

  }
}

