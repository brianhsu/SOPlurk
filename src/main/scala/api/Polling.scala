package org.bone.soplurk.api

import org.bone.soplurk.model._
import org.bone.soplurk.api.PlurkAPI._
import org.bone.soplurk.api.TimelineParser._

import org.scribe.model.Verb

import java.util.Date
import java.util.TimeZone
import java.text.SimpleDateFormat

import scala.util.Try

trait Polling {

  this: PlurkAPI =>

  import MyJValueImplicits._
  import java.text.SimpleDateFormat

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

    /**
     *  Get plurks posted on the user's timeline.
     *
     *  According to Plurk API's document, this API is more efficient than doing Timeline#getPlurks,
     *  so you should this to find out if there is new plurk post on your Timeline.
     *
     *  If `favoeresDetail` / `limitedDetail` / `replurkersDetail` is set to true, the users detail
     *  data will be stored at returned [[org.bone.soplurk.api.PlurkAPI.Timeline]]'s users field.
     *
     *  For example, if you want to fetch all favorers user info at first plurk, you could do
     *  the following.
     *
     *  {{{
     *    val current = new Date
     *    
     *    for {
     *      Timeline(users, plurks) <- api.Polling.getPlurks(current, favorersDetail = true)
     *      firstPlurk              <- plurks.headOption
     *      favorersUID             <- firstPlurk.favoriteInfo.favorers
     *    } {
     *      
     *      val userInfos = users.filterKeys(x => favorersUID.contains(x))
     *
     *      println("plurkURL:" + firstPlurk.plurkURL)
     *      println("favorersUID:" + favorersUID)
     *      println("favorers:" + userInfos)
     *    }
     *    
     *
     *  }}}
     *
     *  @param  offset            Only fetch plurk newer than this date time.
     *  @param  limited           The max number of plurks to be returned.
     *  @param  favorersDatail    Include favorers detail in returned data?
     *  @param  limitedDetail     Include user data of private plurk receivers in returned data?
     *  @param  replurkersDetail  Include replurkers detail data in returned data?
     *  @param  minimalData       Only fetch minimal data of plurk.
     */
    def getPlurks(offset: Date, limit: Int = 20, 
                  favorersDetail: Boolean = false, 
                  limitedDetail: Boolean = false,
                  replurkersDetail: Boolean = false,
                  minimalData: Boolean = false): Try[PlurkAPI.Timeline] = {

      val getPlurkParams = List(
        "offset"            -> toPlurkOffset(offset),
        "limit"             -> limit.toString,
        "favorers_detail"   -> favorersDetail.toString,
        "limited_detail"    -> limitedDetail.toString,
        "replurkers_detail" -> replurkersDetail.toString,
        "minimal_data"      -> minimalData.toString
      ).filterNot(_._2 == "false")

      val response = plurkOAuth.sendRequest(
        "/APP/Polling/getPlurks", Verb.GET, getPlurkParams:_*
      )

      response.map { jsonData =>
        
        PlurkAPI.Timeline(
          users = parseUsersMap(jsonData \ "plurk_users"),
          plurks = parsePlurks(jsonData \ "plurks")
        )

      }

    }

  }

}

