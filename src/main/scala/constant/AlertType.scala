package org.bone.soplurk.constant

import org.bone.soplurk.exceptions._

/**
 *  Represented type of alerts.
 *
 *  @param  word    The alert type value returned by Plurk JSON.
 */
sealed abstract class AlertType(val word: String)

object AlertType
{

  /**
   *  Construct AlertType case object from JSON
   *
   *  @param    word                    The alert type value returned by Plurk JSON.
   *  @throws   NoSuchAlertException    if there is no such alert type.
   */
  def apply(word: String): AlertType = word match {
    case "friendship_request"   => FriendshipRequest
    case "friendship_pending"   => FriendshipPending
    case "friendship_accepted"  => FriendshipAccepted
    case "new_fan"    => NewFan
    case "new_friend" => NewFriend
    case _ => throw new NoSuchAlertTypeException(word)
  }

  case object FriendshipRequest extends AlertType("friendship_request")
  case object FriendshipPending extends AlertType("friendship_pending")
  case object FriendshipAccepted extends AlertType("friendship_accepted")
  case object NewFan extends AlertType("new_fan")
  case object NewFriend extends AlertType("new_friend")
}
