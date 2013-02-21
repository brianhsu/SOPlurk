package org.bone.soplurk.constant

import org.bone.soplurk.exceptions._

sealed abstract class AlertType(val word: String)

object AlertType
{

  def apply(word: String) = word match {
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
