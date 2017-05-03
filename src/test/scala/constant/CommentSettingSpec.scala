package org.bone.soplurk.constant

import org.scalatest.FunSpec
import org.scalatest.Matchers

import org.bone.soplurk.constant._
import org.bone.soplurk.constant.CommentSetting._
import org.bone.soplurk.exceptions._
import org.bone.soplurk.model._

class CommentSettingSpec extends FunSpec with Matchers {

  describe("A CommentSetting") {

    it ("should have correct type code") {
      Everyone.code shouldBe 0
      Disabled.code shouldBe 1
      OnlyFriends.code shouldBe 2
    }

    it ("should able to prase status code to case object") {
      CommentSetting(0) shouldBe Everyone
      CommentSetting(1) shouldBe Disabled
      CommentSetting(2) shouldBe OnlyFriends
    }

    it ("should throw NoSuchCommentSettingException exception when no such code") {
      val exception = intercept[NoSuchCommentSettingException] {
        CommentSetting(3)
      }
      exception.code shouldBe 3
    }
  }
}

