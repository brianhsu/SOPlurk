package org.bone.splurk2.model

import org.bone.splurk2.exceptions._

/**
 *  This class represented a plurk's type, which currently are:
 *
 *   - 0 = Public Plurk
 *   - 1 = Private Plurk
 *   - 2 = Public Plurk (responded by the logged in user)
 *   - 3 = Private Plurk (responded by the logged in user)
 *   - 4 = Anonymous
 *
 *  Each status has a case object that represtend it.
 */
sealed abstract class PlurkType(val code: Byte)

object PlurkType
{
  /**
   *  Get corresponding PlurkType case object according to code.
   *
   *  @param    code    Plurk's type code returned from Plurk's API.
   */
  def apply(code: Byte) = code match {
    case 0 => PublicPlurk
    case 1 => PrivatePlurk
    case 2 => PublicResponded
    case 3 => PrivateResponded
    case 4 => AnonymousPlurk
    case _ => throw new NoSuchPlurkTypeException(code)
  }

  case object PublicPlurk extends PlurkType(0)
  case object PrivatePlurk extends PlurkType(1)
  case object PublicResponded extends PlurkType(2)
  case object PrivateResponded extends PlurkType(3)
  case object AnonymousPlurk extends PlurkType(4)
}
