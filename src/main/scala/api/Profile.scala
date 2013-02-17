package org.bone.soplurk.api

import org.bone.soplurk.model._

import net.liftweb.json.JsonAST._
import org.scribe.model.Verb

import java.util.Date

import scala.util.Try
import scala.util.Failure

trait Profile {

  this: PlurkAPI =>

  import MyJValueImplicits._
  import java.text.SimpleDateFormat

  type Timeline = (Map[Long, User], List[Plurk])

  case class OwnProfile(
    userInfo: ExtendedUser, timeline: Timeline, 
    fansCount: Int, friendsCount: Int, 
    unreadCount: Int, alertsCount: Int,
    privacy: TimelinePrivacy
  )

  object Profile {


    def getOwnProfile: Try[OwnProfile] = {
      val response = plurkOAuth.sendRequest("/APP/Profile/getOwnProfile", Verb.GET)
      response.map { jsonData => 
        val userInfo = ExtendedUser(jsonData \ "user_info")
        val plurks = (jsonData \ "plurks").children.map(Plurk.apply)
        val plurksUsers = (jsonData \ "plurks_users").children.view.map(_.asInstanceOf[JField]).map { x => 
          (x.name.toLong, User(x))
        }.toMap
        
        OwnProfile(
          userInfo = userInfo,
          timeline = (plurksUsers, plurks),
          fansCount = jsonData.get("fans_count"),
          friendsCount = jsonData.get("friends_count"),
          unreadCount = jsonData.get("unread_count"),
          alertsCount = jsonData.get("alerts_count"),
          privacy = TimelinePrivacy(jsonData.get[String]("privacy"))
        )
      }
    }
  }

}

