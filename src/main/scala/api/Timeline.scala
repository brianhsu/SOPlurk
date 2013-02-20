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
     *  Get plurks on current user's timeline.
     *
     *  If `favoeresDetail` / `limitedDetail` / `replurkersDetail` is set to true, the users detail
     *  data will be stored at returned [[org.bone.soplurk.api.PlurkAPI.Timeline]]'s users field.
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

    /**
     *  Get unread plurks on current user's timeline.
     *
     *  If `favoeresDetail` / `limitedDetail` / `replurkersDetail` is set to true, the users detail
     *  data will be stored at returned [[org.bone.soplurk.api.PlurkAPI.Timeline]]'s users field.
     *
     *  @param  offset            Only return plurks newer than this date time.
     *  @param  limit             How many plurks should be returned.
     *  @param  filter            Return only plurks of specific types.
     *  @param  favorersDatail    Include favorers detail in returned data?
     *  @param  limitedDetail     Include user data of private plurk receivers in returned data?
     *  @param  replurkersDetail  Include replurkers detail data in returned data?
     *  @param  minimalData       Only fetch minimal data of plurk.
     */
    def getUnreadPlurks(offset: Option[Date] = None, 
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
        "favorers_detail"   -> favorersDetail.toString,
        "limited_detail"    -> limitedDetail.toString,
        "replurkers_detail" -> replurkersDetail.toString,
        "minimal_data"      -> minimalData.toString
      ).filterNot(x => x._2 == "" || x._2 == "false")

      val response = plurkOAuth.sendRequest(
        "/APP/Timeline/getUnreadPlurks", Verb.GET, 
        params = params: _*
      )

      response.map { jsonData =>

        PlurkAPI.Timeline(
          users  = parseUsersMap(jsonData \ "plurk_users"),
          plurks = parsePlurks(jsonData \ "plurks")
        )

      }

    }


    /**
     *  Get public plurks on specific user's timeline.
     *
     *  If `favoeresDetail` / `limitedDetail` / `replurkersDetail` is set to true, the users detail
     *  data will be stored at returned [[org.bone.soplurk.api.PlurkAPI.Timeline]]'s users field.
     *
     *
     *  @param  nicknameOrID      The nickname or userID of user that you want to fetch.
     *  @param  limit             How many plurks should be returned.
     *  @param  offset            Only return plurks newer than this date time.
     *  @param  filter            Return only plurks of specific types.
     *  @param  favorersDatail    Include favorers detail in returned data?
     *  @param  limitedDetail     Include user data of private plurk receivers in returned data?
     *  @param  replurkersDetail  Include replurkers detail data in returned data?
     *  @param  minimalData       Only fetch minimal data of plurk.
     */
    def getPublicPlurks(nicknameOrID: String,
                        limit: Int = 20,
                        offset: Option[Date] = None, 
                        filter: Option[Filter] = None,
                        favorersDetail: Boolean = false,
                        limitedDetail: Boolean = false,
                        replurkersDetail: Boolean = false,
                        minimalData: Boolean = false): Try[PlurkAPI.Timeline] = {
      
      val params = List(
        "user_id" -> nicknameOrID,
        "offset"  -> offset.map(toPlurkOffset).getOrElse(""),
        "limit"   -> limit.toString,
        "filter"  -> filter.map(_.word).getOrElse(""),
        "favorers_detail"   -> favorersDetail.toString,
        "limited_detail"    -> limitedDetail.toString,
        "replurkers_detail" -> replurkersDetail.toString,
        "minimal_data"      -> minimalData.toString
      ).filterNot(x => x._2 == "" || x._2 == "false")

      val response = plurkOAuth.sendRequest(
        "/APP/Timeline/getPublicPlurks", Verb.GET, 
        params = params: _*
      )

      response.map { jsonData =>

        PlurkAPI.Timeline(
          users  = parseUsersMap(jsonData \ "plurk_users"),
          plurks = parsePlurks(jsonData \ "plurks")
        )

      }

    }

    /**
     *  Add plurk to current user's timeline.
     *
     *  @param  content     The Plurk's text.
     *  @param  qualifier   The Plurk's qualifier.
     *  @param  limitedTo   Who can see this plurk? 
     *                      `Nil` for everyone, `List(0)` for only friends, 
     *                      `List(123, 379)` for those whoes user id is in the list.
     *  @param  noComment   Only users set by this field can comment on this plurk.
     *  @param  language    The plurk's language code. 
     *                      See [[http://www.plurk.com/API/2#/APP/Timeline/plurkAdd PlurkAPI Document]]
     */
    def plurkAdd(content: String, 
                 qualifier: Qualifier, 
                 limitedTo: List[Long] = Nil, 
                 commentSetting: Option[WritableCommentSetting] = None,
                 language: Option[String] = None): Try[Plurk] = {

      
      def formatLimitedTo: String = limitedTo match {
        case Nil => ""
        case xs  => xs.mkString("[", ",", "]")
      }

      val params = List(
        "content" -> content,
        "qualifier" -> qualifier.name,
        "limitedTo" -> formatLimitedTo,
        "noComment" -> commentSetting.map(_.code.toString).getOrElse(""),
        "language"  -> language.getOrElse("")
      ).filterNot(_._2.isEmpty)

      val response = plurkOAuth.sendRequest(
        "/APP/Timeline/plurkAdd", Verb.POST, 
        params = params: _*
      )

      response.map { jsonData => Plurk(jsonData) }

    }

  }

}

