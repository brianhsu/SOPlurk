package org.bone.soplurk.api

import org.bone.soplurk.model._
import org.bone.soplurk.api.PlurkAPI._
import org.bone.soplurk.api.TimelineParser._

import org.scribe.model.Verb

import scala.util.Try
import java.util.Date

trait FriendsFans {

  this: PlurkAPI =>

  import MyJValueImplicits._

  /**
   *  API of /APP/FriendsFans/
   */
  object FriendsFans {

    /**
     *  Get Fans list of a user.
     *
     *  @param  userID    Which user you want to fetch his / her fans list?
     *  @param  limit     Return how many fans in chunk?
     *  @param  offset    The offset when fetching fans list.
     *
     *  @return           Success[List[ExtendedUser]] if everything is go fine.
     */
    def getFansByOffset(userID: Long, limit: Int = 10, 
                        offset: Option[Int] = None): Try[List[ExtendedUser]] = {

      val requiredParams = List("user_id" -> userID.toString, "limit" -> limit.toString)
      val optionalParams = List(offset.map("offset" -> _.toString))
      val params = requiredParams ++ optionalParams.flatten

      val response = plurkOAuth.sendRequest(
        "/APP/FriendsFans/getFansByOffset", Verb.GET, params: _*
      )

      response.map { jsonData => jsonData.children.map(x => ExtendedUser(x)) }
      
    }

    /**
     *  Get Friend list of a user.
     *
     *  @param  userID    Which user you want to fetch his / her friend list?
     *  @param  limit     Return how many fans in chunk?
     *  @param  offset    The offset when fetching fans list.
     *
     *  @return           Success[List[ExtendedUser]] if everything is go fine.
     */
    def getFriendsByOffset(userID: Long, limit: Int = 10, 
                           offset: Option[Int] = None): Try[List[ExtendedUser]] = {

      val requiredParams = List("user_id" -> userID.toString, "limit" -> limit.toString)
      val optionalParams = List(offset.map("offset" -> _.toString))
      val params = requiredParams ++ optionalParams.flatten

      val response = plurkOAuth.sendRequest(
        "/APP/FriendsFans/getFriendsByOffset", Verb.GET, params: _*
      )

      response.map { jsonData => jsonData.children.map(x => ExtendedUser(x)) }
      
    }

    /**
     *  Get list of users that logged in user is following.
     *
     *  @param  limit     Return how many fans in chunk?
     *  @param  offset    The offset when fetching fans list.
     *
     *  @return           Success[List[ExtendedUser]] if everything is go fine.
     */
    def getFollowingByOffset(limit: Int = 10, 
                             offset: Option[Int] = None): Try[List[ExtendedUser]] = {

      val requiredParams = List("limit" -> limit.toString)
      val optionalParams = List(offset.map("offset" -> _.toString))
      val params = requiredParams ++ optionalParams.flatten

      val response = plurkOAuth.sendRequest(
        "/APP/FriendsFans/getFollowingByOffset", Verb.GET, params: _*
      )

      response.map { jsonData => jsonData.children.map(x => ExtendedUser(x)) }
      
    }

    /**
     *  Ask to become friend with other user.
     *
     *  Ask permission to become friend with other user, the user with `userID`
     *  has to accept a friendship.
     *
     *  @param  userID  The unique id of user that you want to be friend.
     *  @return         Success[Boolean](true) if everything is fine.
     */
    def becomeFriend(userID: Long): Try[Boolean] = {

      val response = plurkOAuth.sendRequest(
        "/APP/FriendsFans/becomeFriend", Verb.POST,
        "friend_id" -> userID.toString
      )

      response.map { jsonData => jsonData.get[String]("success_text") == "ok" }
      
    }

    /**
     *  Remove friendship with user.
     *
     *  @param  userID  The user's id that you want to remove as friend.
     *  @return         Success[Boolean](true) if everything is fine.
     */
    def removeAsFriend(userID: Long): Try[Boolean] = {

      val response = plurkOAuth.sendRequest(
        "/APP/FriendsFans/removeAsFriend", Verb.POST,
        "friend_id" -> userID.toString
      )

      response.map { jsonData => jsonData.get[String]("success_text") == "ok" }
      
    }

    /**
     *  Become a fan of other user.
     *
     *  If you want stop being a fan of someone, use `setFollowing(userID, false)`.
     *
     *  @param  userID  The unique id of user that you want to be his / her fan.
     *  @return         Success[Boolean](true) if everything is fine.
     */
    def becomeFan(userID: Long): Try[Boolean] = {

      val response = plurkOAuth.sendRequest(
        "/APP/FriendsFans/becomeFan", Verb.POST,
        "fan_id" -> userID.toString
      )

      response.map { jsonData => jsonData.get[String]("success_text") == "ok" }
    }

    /**
     *  Update following status of a user.
     *
     *  A user can befriend someone, but can unfollow them. 
     *
     *  This request is also used to stop following someone as a fan.
     *
     *  @param    userID    The ID of user that you want to follow / unfollow
     *  @param    follow    `true` if want to following, `false` if want to unfollow.
     *  @return             Success[Boolean](true) if everything is OK.
     */
    def setFollowing(userID: Long, following: Boolean): Try[Boolean] = {

      val response = plurkOAuth.sendRequest(
        "/APP/FriendsFans/setFollowing", Verb.POST,
        "user_id" -> userID.toString,
        "follow"  -> following.toString
      )

      response.map { jsonData => jsonData.get[String]("success_text") == "ok" }
    }

  }

}

