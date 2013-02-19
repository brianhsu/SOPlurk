package org.bone.soplurk.api

import org.bone.soplurk.model._
import org.bone.soplurk.api.PlurkAPI._
import org.bone.soplurk.api.TimelineParser._

import org.scribe.model.Verb

import scala.util.Try
import java.util.Date

trait Timeline {

  this: PlurkAPI =>

  import MyJValueImplicits._
  import java.text.SimpleDateFormat

  /**
   *  API of /APP/Polling/
   */
  object Timeline {

    import TimelineParser._

    /**
     *  Get a specific plurk's data
     *
     *  If `favoeresDetail` / `limitedDetail` / `replurkersDetail` is set to true, the users detail
     *  data will be stored at returned [[org.bone.soplurk.api.PlurkAPI.PlurkData]]'s users field.
     *
     *  @param  plurkID           Plurk's unique ID.
     *  @param  favorersDatail    Include favorers detail in returned data?
     *  @param  limitedDetail     Include user data of private plurk receivers in returned data?
     *  @param  replurkersDetail  Include replurkers detail data in returned data?
     *  @param  minimalData       Only fetch minimal data of plurk.
     */
    def getPlurk(plurkID: Long, 
                 favorersDetail: Boolean = false, 
                 limitedDetail: Boolean = false,
                 replurkersDetail: Boolean = false,
                 minimalData: Boolean = false): Try[PlurkData] = {

      val params = List(
        "plurk_id" -> plurkID.toString,
        "favorers_detail" -> favorersDetail.toString,
        "limited_detail" -> limitedDetail.toString,
        "replurkers_detail" -> replurkersDetail.toString,
        "minimal_data" -> minimalData.toString
      ).filterNot(_._2 == "false")

      val response = plurkOAuth.sendRequest("/APP/Timeline/getPlurk", Verb.GET, params:_*)

      response.map { jsonData =>
        
        PlurkData(
          author = User(jsonData \ "user"),
          users = parseUsersMap(jsonData \ "plurk_users"),
          plurk = Plurk(jsonData \ "plurk")
        )
      }

    }

    /**
     *  Get plurks on users timeline.
     *
     *  If `favoeresDetail` / `limitedDetail` / `replurkersDetail` is set to true, the users detail
     *  data will be stored at returned [[org.bone.soplurk.api.PlurkAPI.PlurkData]]'s users field.
     *
     *  @param  offset            Only return plurks newer than this date time.
     *  @param  limit             How many plurks should be returned.
     *  @param  filter            Return only plurks of specific types.
     *  @param  favorersDatail    Include favorers detail in returned data?
     *  @param  limitedDetail     Include user data of private plurk receivers in returned data?
     *  @param  replurkersDetail  Include replurkers detail data in returned data?
     *  @param  minimalData       Only fetch minimal data of plurk.
     */
    def getPlurks(offset: Option[Date] = None, 
                  limit: Int = 20,
                  filter: Option[Filter] = None,
                  favorersDetail: Boolean = false,
                  limitedDetail: Boolean = false,
                  replurkersDetail: Boolean = false,
                  minimalData: Boolean = false): Try[PlurkAPI.Timeline] = {
      
      val params = List(
        "offset" -> offset.map(toPlurkOffset).getOrElse(""),
        "limit"  -> limit.toString,
        "filter" -> filter.map(_.word).getOrElse(""),
        "favorers_detail" -> favorersDetail.toString,
        "limited_detail" -> limitedDetail.toString,
        "replurkers_detail" -> replurkersDetail.toString,
        "minimal_data" -> minimalData.toString
      ).filterNot(x => x._2 == "" || x._2 == "false")

      val response = plurkOAuth.sendRequest("/APP/Timeline/getPlurks", Verb.GET, params: _*)

      response.map { jsonData =>

        PlurkAPI.Timeline(
          users  = parseUsersMap(jsonData \ "plurk_users"),
          plurks = parsePlurks(jsonData \ "plurks")
        )

      }

    }



  }

}

