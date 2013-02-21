package org.bone.soplurk.constant

import org.bone.soplurk.exceptions._

/**
 *  This class represented a user's birthday information privacy setting.
 */
sealed abstract class BirthdayPrivacy(val code: Byte)

object BirthdayPrivacy {

  /**
   *  Get corresponding BirthdayPrivacy case object according to code.
   *
   *  @param    code    bday_privacy code returned by Plurk's JSON API.
   *  @return           Corresponding case object.
   *  @throws           NoSuchBirthdayPrivacyException if no matched code.
   */
  def apply(code: Byte) = code match {
    case 0 => HideAll
    case 1 => HideYear
    case 2 => ShowAll
    case _ => throw new NoSuchBirthdayPrivacyException(code)
  }

  case object HideAll extends BirthdayPrivacy(0)
  case object HideYear extends BirthdayPrivacy(1)
  case object ShowAll extends BirthdayPrivacy(2)
}

