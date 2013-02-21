package org.bone.soplurk.constant

import org.bone.soplurk.exceptions._

/**
 *  Represented a user's gender.
 *
 *  @param  code   The code returned by Plurk's JSON API.
 */
sealed abstract class Gender(val code: Byte)

/**
 *  Represtened a user's gender
 */
object Gender {

  /**
   *  Get corresponding Gender case object according to code.
   *
   *  @param    code    gender code returned by Plurk's JSON API.
   *  @return           Corresponding case object.
   *  @throws           NoSuchGenderException if no matched code.
   */
  def apply(code: Byte): Gender = code match {
    case 0 => Female
    case 1 => Male
    case 2 => NotStating
    case _ => throw new NoSuchGenderException(code)
  }

  case object Female extends Gender(0)
  case object Male extends Gender(1)
  case object NotStating extends Gender(2)
}

