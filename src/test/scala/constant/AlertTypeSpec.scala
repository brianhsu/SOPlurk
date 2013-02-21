package org.bone.soplurk.constant

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

import org.bone.soplurk.constant._
import org.bone.soplurk.constant.AlertType._
import org.bone.soplurk.exceptions._
import org.bone.soplurk.model._

class AlertTypeSpec extends FunSpec with ShouldMatchers {

  describe("A AlertType") {

    it ("should have correct type word") {
      FriendshipRequest.word should be === "friendship_request"
      FriendshipPending.word should be === "friendship_pending"
      FriendshipAccepted.word should be === "friendship_accepted"
      NewFan.word should be === "new_fan"
      NewFriend.word should be === "new_friend"
    }

    it ("should able to prase status code to case object") {
      AlertType("friendship_request") should be === FriendshipRequest
      AlertType("friendship_pending") should be === FriendshipPending
      AlertType("friendship_accepted") should be === FriendshipAccepted
      AlertType("new_fan") should be === NewFan
      AlertType("new_friend") should be === NewFriend
    }

    it ("should get throw NoSuchAlertTypeException when occurs unknown type") {
      val exception = intercept[NoSuchAlertTypeException] { AlertType("unknown") }
      exception.word should be === "unknown"
    }
  }
}

