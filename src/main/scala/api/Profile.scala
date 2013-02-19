package org.bone.soplurk.api

import org.bone.soplurk.api.PlurkAPI._
import org.bone.soplurk.model._

import org.scribe.model.Verb

import scala.util.Try

trait Profile {

  this: PlurkAPI =>

  import MyJValueImplicits._


  /**
   *  API of /APP/Profile/
   */
  object Profile {

    import TimelineParser._

    /**
     *  Get current user's profile.
     *
     *  @return   Success[OwnProfile] is it get data from Plurk correctly.
     */
    def getOwnProfile: Try[OwnProfile] = {
      val response = plurkOAuth.sendRequest("/APP/Profile/getOwnProfile", Verb.GET)

      response.map { jsonData => 

        val userInfo = ExtendedUser(jsonData \ "user_info")
        val plurks = parsePlurks(jsonData \ "plurks")
        val plurksUsers = parseUsersMap(jsonData \ "plurks_users") 
        
        OwnProfile(
          userInfo = userInfo,
          timeline = PlurkAPI.Timeline(plurksUsers, plurks),
          fansCount = jsonData.get("fans_count"),
          friendsCount = jsonData.get("friends_count"),
          unreadCount = jsonData.get("unread_count"),
          alertsCount = jsonData.get("alerts_count"),
          privacy = TimelinePrivacy(jsonData.get[String]("privacy"))
        )
      }
    }

    /**
     *  Get public profile of a user.
     *
     *  @param    userID        User's id that you want to fetch his / her timeline.
     *
     *  @return                 Success[PublicProfile] if get data correctly.
     */
    def getPublicProfile(userID: Long): Try[PublicProfile] = getPublicProfile(userID.toString)

    /**
     *  Get public profile of a user.
     *
     *  @param    nickname      User's nickname that you want to fetch his / her timeline.
     *
     *  @return                 Success[PublicProfile] if get data correctly.
     */
    def getPublicProfile(nickname: String): Try[PublicProfile] = {
      val response = plurkOAuth.sendRequest(
        "/APP/Profile/getPublicProfile", Verb.GET,
        "user_id" -> nickname
      )

      response.map { jsonData =>

        PublicProfile(
          userInfo = ExtendedUser(jsonData \ "user_info"),
          plurks = parsePlurks(jsonData \ "plurks"),
          fansCount = jsonData.get("fans_count"),
          friendsCount = jsonData.get("friends_count"),
          privacy = TimelinePrivacy(jsonData.get[String]("privacy")),
          hasReadPermission = jsonData.get("has_read_permission"),
          isFan = jsonData.getOption("is_fan"),
          areFriends = jsonData.getOption("are_friends"),
          isFollowing = jsonData.getOption("is_following")
        )

      }

    }
  }

}

