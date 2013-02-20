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

  }

}

