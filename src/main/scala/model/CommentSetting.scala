package org.bone.soplurk.model

import scala.annotation._
import org.bone.soplurk.exceptions._

/**
 *  This class represented who can comment on the Plurks.
 *  
 *  There are three possible setting: everyone, comment is disabled, only friends can comment.
 *
 *  Each of these setting are represtend as a case object in CommentSetting object.
 *
 *  @param  code    The setting code in Plurk's json API.
 */
sealed abstract class CommentSetting(val code: Byte)

abstract class WritableCommentSetting(code: Byte) extends CommentSetting(code)

object CommentSetting {

  /**
   *  Everyone can comment on the plurk.
   */
  case object Everyone extends CommentSetting(0)

  /**
   *  Comment is disabled.
   */
  case object Disabled extends WritableCommentSetting(1)

  /**
   *  Only friends can comment on it.
   */
  case object OnlyFriends extends WritableCommentSetting(2)

  /**
   *  Generate CommentSetting from the code returned by Plurk.
   *
   *  @param    code    no_comment code returned by Plurk's JSON API.
   *  @return           Corresponding case object.
   *  @throws           NoSuchCommentSettingException if no matched code.
   */
  def apply(code: Byte) = code match {
    case 0 => Everyone
    case 1 => Disabled
    case 2 => OnlyFriends
    case _ => throw new NoSuchCommentSettingException(code)
  }
}

