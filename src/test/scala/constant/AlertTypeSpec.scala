package org.bone.soplurk.constant

import org.scalatest.FunSpec
import org.scalatest.Matchers

import org.bone.soplurk.constant._
import org.bone.soplurk.constant.AlertType._
import org.bone.soplurk.exceptions._
import org.bone.soplurk.model._

class AlertTypeSpec extends FunSpec with Matchers {

  describe("A AlertType") {

    it ("should have correct type word") {
      FriendshipRequest.word shouldBe "friendship_request"
      FriendshipPending.word shouldBe "friendship_pending"
      FriendshipAccepted.word shouldBe "friendship_accepted"
      NewFan.word shouldBe "new_fan"
      NewFriend.word shouldBe "new_friend"
    }

    it ("should able to prase status code to case object") {
      AlertType("friendship_request") shouldBe FriendshipRequest
      AlertType("friendship_pending") shouldBe FriendshipPending
      AlertType("friendship_accepted") shouldBe FriendshipAccepted
      AlertType("new_fan") shouldBe NewFan
      AlertType("new_friend") shouldBe NewFriend
    }

    it ("should get throw NoSuchAlertTypeException when occurs unknown type") {
      val exception = intercept[NoSuchAlertTypeException] { AlertType("unknown") }
      exception.word shouldBe "unknown"
    }
  }
}

