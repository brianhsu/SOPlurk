package org.bone.soplurk.constant

import org.bone.soplurk.exceptions._

/**
 *  This class represented a plurk's status, either read, unread, or muted.
 *
 *  Each status has a case object that represtend it.
 */
sealed abstract class ReadStatus(val code: Byte)

object ReadStatus
{
  /**
   *  Get corresponding ReadStatus case object according to code.
   *
   *  @param    code    Status code returned from Plurk's API.
   *  @return           Corresponding case object.
   *  @throws           NoSuchReadStatusException if no matched code.
   */
  def apply(code: Byte): ReadStatus = code match {
    case 0 => Read
    case 1 => Unread
    case 2 => Muted
    case _ => throw new NoSuchReadStatusException(code)
  }

  case object Read extends ReadStatus(0)
  case object Unread extends ReadStatus(1)
  case object Muted extends ReadStatus(2)

}
