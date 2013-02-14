package org.bone.splurk2.model

/**
 *  This class represented a plurk's type, which currently are:
 *
 *   - 0 = Public Plurk
 *   - 1 = Private Plurk
 *   - 2 = Public Plurk (responded by the logged in user)
 *   - 3 = Private Plurk (responded by the logged in user)
 *   - 4 = Anonymous Plurk
 *   - 6 = Anonymous Plurk (reponded by the logged in user)
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
    case 0 => Public
    case 1 => Private
    case 2 => PublicResponded
    case 3 => PrivateResponded
    case 4 => Anonymous
    case 6 => AnonymousResponded
    case _ => new PlurkType(code) {}
  }

  case object Public extends PlurkType(0)
  case object Private extends PlurkType(1)
  case object PublicResponded extends PlurkType(2)
  case object PrivateResponded extends PlurkType(3)
  case object Anonymous extends PlurkType(4)
  case object AnonymousResponded extends PlurkType(6)

}
