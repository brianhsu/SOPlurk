package org.bone.soplurk.api

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.PrivateMethodTester 

class PlurkAPISpec extends FunSpec with ShouldMatchers {

  describe("A PlurkAPI") {

    it ("should throw IllegalStateException when calling authorize before getAuthorizationURL") {

      intercept[IllegalStateException] {
        PlurkAPI.withoutCallback("apiKey", "apiSecret").authorize("code")
      }

    }

  }
}

