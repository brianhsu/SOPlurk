package org.bone.splurk2.model

import scala.annotation._
import org.bone.splurk2.exceptions._

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

object CommentSetting {

  /**
   *  Everyone can comment on the plurk.
   */
  case object Everyone extends CommentSetting(0)

  /**
   *  Comment is disabled.
   */
  case object Disabled extends CommentSetting(1)

  /**
   *  Only friends can comment on it.
   */
  case object OnlyFriends extends CommentSetting(2)

  /**
   *  Generate CommentSetting from the code returned by Plurk.
   */
  def apply(code: Byte) = code match {
    case 0 => Everyone
    case 1 => Disabled
    case 2 => OnlyFriends
    case _ => throw new NoSuchCommentSettingException(code)
  }
}

