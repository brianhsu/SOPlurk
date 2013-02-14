package org.bone.splurk2.model

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

import org.bone.splurk2.exceptions._
import org.bone.splurk2.model._
import org.bone.splurk2.model.CommentSetting._

class CommentSettingSpec extends FunSpec with ShouldMatchers {

  describe("A CommentSetting") {

    it ("should have correct type code") {
      Everyone.code should be === 0
      Disabled.code should be === 1
      OnlyFriends.code should be === 2
    }

    it ("should able to prase status code to case object") {
      CommentSetting(0) should be === Everyone
      CommentSetting(1) should be === Disabled
      CommentSetting(2) should be === OnlyFriends
    }

    it ("should throw NoSuchCommentSettingException exception when no such code") {
      val exception = intercept[NoSuchCommentSettingException] {
        CommentSetting(3)
      }
      exception.code should be === 3
    }
  }
}

