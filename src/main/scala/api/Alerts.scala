package org.bone.soplurk.api

import org.bone.soplurk.model._
import org.bone.soplurk.api.PlurkAPI._
import org.bone.soplurk.api.TimelineParser._

import org.scribe.model.Verb

import scala.util.Try

trait Alerts {

  this: PlurkAPI =>

  import MyJValueImplicits._

  /**
   *  API of /APP/Alerts/
   */
  object Alerts {
    
    /**
     *  Get current user's active alerts list
     *
     *  @return   Success[List[Alert]] if everything is fine.
     */
    def getActive: Try[List[Alert]] = {

      val response = plurkOAuth.sendRequest("/APP/Alerts/getActive", Verb.GET)

      response.map { jsonData => 
        jsonData.children.map(alertJS => Alert(alertJS)) 
      }

    }

    /**
     *  Get 30 past alerts
     *
     *  @return   Success[List[Alert]] of 30 past alerts if everything is OK.
     */
    def getHistory: Try[List[Alert]] = {

      val response = plurkOAuth.sendRequest("/APP/Alerts/getHistory", Verb.GET)

      response.map { jsonData => 
        jsonData.children.map(alertJS => Alert(alertJS)) 
      }

    }

    /**
     *  Accept friend request as fan.
     *
     *  @param    userID    The id of user that has asked for friendship.
     *  @return             Success[Boolean](true) if everything is OK.
     */
    def addAsFan(userID: Long): Try[Boolean] = {

      val response = plurkOAuth.sendRequest(
        "/APP/Alerts/addAsFan", Verb.POST,
        "user_id" -> userID.toString
      )

      response.map { jsonData => jsonData.get[String]("success_text") == "ok" }
    }

    /**
     *  Accept friend request as friend.
     *
     *  @param    userID    The id of user that has asked for friendship.
     *  @return             Success[Boolean](true) if everything is OK.
     */
    def addAsFriend(userID: Long): Try[Boolean] = {

      val response = plurkOAuth.sendRequest(
        "/APP/Alerts/addAsFriend", Verb.POST,
        "user_id" -> userID.toString
      )

      response.map { jsonData => jsonData.get[String]("success_text") == "ok" }
    }

    /**
     *  Accept all friend request as fans.
     *
     *  @return   Success[Boolean](true) if everything is OK.
     */
    def addAllAsFan(): Try[Boolean] = {

      val response = plurkOAuth.sendRequest("/APP/Alerts/addAllAsFan", Verb.POST)
      response.map { jsonData => jsonData.get[String]("success_text") == "ok" }
    }

    /**
     *  Accept all friend request as friends.
     *
     *  @return   Success[Boolean](true) if everything is OK.
     */
    def addAllAsFriends(): Try[Boolean] = {

      val response = plurkOAuth.sendRequest("/APP/Alerts/addAllAsFriends", Verb.POST)
      response.map { jsonData => jsonData.get[String]("success_text") == "ok" }
    }

    /**
     *  Deny other user's friend request.
     *
     *  @param    userID    The id of user that has asked for friendship.
     *  @return             Success[Boolean](true) if everything is OK.
     */
    def denyFriendship(userID: Long): Try[Boolean] = {

      val response = plurkOAuth.sendRequest(
        "/APP/Alerts/denyFriendship", Verb.POST,
        "user_id" -> userID.toString
      )

      response.map { jsonData => jsonData.get[String]("success_text") == "ok" }
    }

  }

}

