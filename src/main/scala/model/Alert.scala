package org.bone.soplurk.model

import org.bone.soplurk.constant._
import org.bone.soplurk.constant.AlertType._

import net.liftweb.json.JValue

import java.util.Date
import java.util.Locale
import java.text.SimpleDateFormat

/**
 *  Represented Alert in Plurk.
 *
 *  @param  alertType   Type of this alert.
 *  @param  user        The user information about this alert.
 *  @param  posted      When did this alert occurred.
 */
case class Alert(alertType: AlertType, user: User, posted: Date)

object Alert {

  import MyJValueImplicits._

  private def toDate(dateString: String): Date = {
    val dateFormatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US)
    dateFormatter.parse(dateString)
  }

  /**
   *  Create Alert object from JSON returned by Plurk.
   *
   *  @param    jsonData  JSON data of alert returned by Plurk.
   *  @return             Alert object corresponding to the jsonData.
   */
  def apply(jsonData: JValue): Alert = {
    
    val alertType = AlertType(jsonData.get[String]("type"))
    val user = getUserData(alertType, jsonData)
    val posted = toDate(jsonData.get[String]("posted"))

    Alert(alertType, user, posted)
  }

  /**
   *  Get user data from Alert JSON
   *
   *  @param    alertType   The type of alert.
   *  @param    jsonData    The alert JSON object.
   *  @return               User's information about this alert.
   */
  private def getUserData(alertType: AlertType, jsonData: JValue) = {
    alertType match {
      case FriendshipRequest  => User(jsonData \ "from_user")
      case FriendshipPending  => User(jsonData \ "to_user")
      case FriendshipAccepted => User(jsonData \ "friend_info")
      case NewFan    => User(jsonData \ "new_fan")
      case NewFriend => User(jsonData \ "new_friend")
    }
  }

}

