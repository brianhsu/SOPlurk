package org.bone.soplurk.api

import org.bone.soplurk.model._

import org.scribe.model.Verb

import java.util.Date

import scala.util.Try

trait Polling {

  this: PlurkAPI =>

  import MyJValueImplicits._
  import java.text.SimpleDateFormat

  /**
   *  Unread plurks count
   *
   *  @param  all             Number of all unread plurks.
   *  @param  my              Number of unread plurks that is posted by current user.
   *  @param  privatePlurks   Number of unread plurks that is private plurk.
   *  @param  responded       Number of unread plurks that is responded by current user.
   *  @param  favorite        Number of unread favorite plurks that is favorited by user.
   */
  case class UnreadCount(
    all: Int, my: Int, privatePlurks: Int, 
    responded: Int, favorite: Int
  )

  /**
   *  API of /APP/Polling/
   */
  object Polling {

    import TimelineParser._

    /**
     *  Get unread count on a user's timeline.
     *
     *  @return   Success[UnreadCount] if get data correctly.
     */
    def getUnreadCount: Try[UnreadCount] = {
      val response = plurkOAuth.sendRequest("/APP/Polling/getUnreadCount", Verb.GET)

      response.map { jsonData =>
        UnreadCount (
          all = jsonData.get("all"),
          my = jsonData.get("my"),
          privatePlurks = jsonData.get("private"),
          responded = jsonData.get("responded"),
          favorite = jsonData.get("favorite")
        )
      }
    }

  }

}

