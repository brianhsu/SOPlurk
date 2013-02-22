package org.bone.soplurk.api

import org.bone.soplurk.api.PlurkAPI._
import org.bone.soplurk.api.TimelineParser._
import org.bone.soplurk.model._
import org.bone.soplurk.constant._

import org.scribe.model.Verb

import net.liftweb.json.JsonAST._

import scala.util.Try

import java.io.File
import java.util.Date

trait Timeline {

  this: PlurkAPI =>

  import MyJValueImplicits._
  import java.text.SimpleDateFormat

  /**
   *  API of /APP/Timeline/
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
     *
     *  @return                   Success[PlurkData] if get data from Plurk correctly.
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
     *
     *  @return                   Success[Timeline] if get data from Plurk correctly.
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
     *
     *  @return                   Success[Timeline] if get data from Plurk correctly.
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
     *
     *  @return                   Success[Timeline] if get data from Plurk correctly.
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
     *
     *  @return             Success[Plurk] if get data from Plurk correctly.
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

    /**
     *  Delete a Plurk.
     *
     *  @param      plurkID     Plurk's unique id that you want to delete.
     *
     *  @return                 Success[Boolean](true) if delete successfuly.
     */
    def plurkDelete(plurkID: Long): Try[Boolean] = {

      val response = plurkOAuth.sendRequest(
        "/APP/Timeline/plurkDelete", Verb.POST, 
        "plurk_id" -> plurkID.toString
      )

      response.map { jsonData => 
        jsonData.get[String]("success_text") == "ok"
      }

    }

    /**
     *  Edit a Plurk.
     *
     *  @param      plurkID     Plurk's unique id that you want to delete.
     *  @param      content     New content of this plurk.
     *  @return                 Success[Plurk] which contains new plurk data if edit successfully.
     */
    def plurkEdit(plurkID: Long, content: String): Try[Plurk] = {

      val response = plurkOAuth.sendRequest(
        "/APP/Timeline/plurkEdit", Verb.POST, 
        "plurk_id" -> plurkID.toString,
        "content"  -> content
      )

      response.map { jsonData => Plurk(jsonData) }

    }

    /**
     *  Mute plurks.
     *
     *  @param      plurkIDs    List of Plurk's id that you want to mute.
     *  @return                 Success[Boolean](true) if delete successfuly.
     */
    def mutePlurks(plurkIDs: List[Long]): Try[Boolean] = {

      val response = plurkOAuth.sendRequest(
        "/APP/Timeline/mutePlurks", Verb.POST, 
        "ids" -> plurkIDs.mkString("[", ",", "]")
      )

      response.map { jsonData => 
        jsonData.get[String]("success_text") == "ok"
      }
    }

    /**
     *  Unmute plurks.
     *
     *  @param      plurkIDs    List of Plurk's id that you want to unmute.
     *  @return                 Success[Boolean](true) if delete successfuly.
     */
    def unmutePlurks(plurkIDs: List[Long]): Try[Boolean] = {

      val response = plurkOAuth.sendRequest(
        "/APP/Timeline/unmutePlurks", Verb.POST, 
        "ids" -> plurkIDs.mkString("[", ",", "]")
      )

      response.map { jsonData => 
        jsonData.get[String]("success_text") == "ok"
      }

    }

    /**
     *  Favorite plurks.
     *
     *  @param      plurkIDs    List of Plurk's id that you want to favorite.
     *  @return                 Success[Boolean](true) if delete successfuly.
     */
    def favoritePlurks(plurkIDs: List[Long]): Try[Boolean] = {

      val response = plurkOAuth.sendRequest(
        "/APP/Timeline/favoritePlurks", Verb.POST, 
        "ids" -> plurkIDs.mkString("[", ",", "]")
      )

      response.map { jsonData => 
        jsonData.get[String]("success_text") == "ok"
      }
    }

    /**
     *  Unfavorite plurks.
     *
     *  @param      plurkIDs    List of Plurk's id that you want to unfavorite.
     *  @return                 Success[Boolean](true) if delete successfuly.
     */
    def unfavoritePlurks(plurkIDs: List[Long]): Try[Boolean] = {

      val response = plurkOAuth.sendRequest(
        "/APP/Timeline/unfavoritePlurks", Verb.POST, 
        "ids" -> plurkIDs.mkString("[", ",", "]")
      )

      response.map { jsonData => 
        jsonData.get[String]("success_text") == "ok"
      }
    }

    /**
     *  Replurk plurks.
     *
     *  Returned data will be a `Success` with `(isSuccess, Map[Long, ReplurkStatus])`,
     *  which `isSuccess` in the first element is a Boolean value, will be `true` if all
     *  `plurkIds` has been replurked correctly.
     *
     *  The second element is `Map[Long, ReplurkStatus]`, where the key is the plurk id
     *  you asked to replurk at `plurkIDs`, the value is status about whether replurk on 
     *  this id is success, and information about that plurk itself.
     *
     *  @param      plurkIDs    List of Plurk's id that you want to replurk.
     *  @return                 Success[(isSuccess, Map[Long, ReplurkStatus])] if everything is fine.
     */
    def replurk(plurkIDs: List[Long]): Try[(Boolean, Map[Long, ReplurkStatus])] = {

      val response = plurkOAuth.sendRequest(
        "/APP/Timeline/replurk", Verb.POST, 
        "ids" -> plurkIDs.mkString("[", ",", "]")
      )

      response.map { jsonData => 
        val isSuccess = jsonData.get[Boolean]("success")
        val statusMap = (jsonData \ "results").children.map { result =>

          val plurkID = result.asInstanceOf[JField].name.toLong
          val plurk = Plurk(result \ "plurk")
          val error = result.getOption[String]("error").filterNot(_.isEmpty)

          (plurkID, ReplurkStatus(error, plurk))

        }.toMap

        (isSuccess, statusMap)
      }
    }

    /**
     *  Unreplurk plurks.
     *
     *  Returned data will be a `Success` with `(isSuccess, Map[Long, ReplurkStatus])`,
     *  which `isSuccess` in the first element is a Boolean value, will be `true` if all
     *  `plurkIds` has been unreplurked correctly.
     *
     *  The second element is `Map[Long, ReplurkStatus]`, where the key is the plurk id
     *  you asked to replurk at `plurkIDs`, the value is status about whether unreplurk on 
     *  this id is success, and information about that plurk itself.
     *
     *  @param      plurkIDs    List of Plurk's id that you want to unreplurk.
     *  @return                 Success[(isSuccess, Map[Long, ReplurkStatus])] if everything is fine.
     */
    def unreplurk(plurkIDs: List[Long]): Try[(Boolean, Map[Long, ReplurkStatus])] = {

      val response = plurkOAuth.sendRequest(
        "/APP/Timeline/unreplurk", Verb.POST, 
        "ids" -> plurkIDs.mkString("[", ",", "]")
      )

      response.map { jsonData => 
        val isSuccess = jsonData.get[Boolean]("success")
        val statusMap = (jsonData \ "results").children.map { result =>

          val plurkID = result.asInstanceOf[JField].name.toLong
          val plurk = Plurk(result \ "plurk")
          val error = result.getOption[String]("error").filterNot(_.isEmpty)

          (plurkID, ReplurkStatus(error, plurk))

        }.toMap

        (isSuccess, statusMap)
      }
    }

    /**
     *  Mark plurks as read.
     *
     *  @param      plurkIDs    List of Plurk's id that you want to mark as read.
     *  @return                 Success[Boolean](true) if delete successfuly.
     */
    def markAsRead(plurkIDs: List[Long]): Try[Boolean] = {

      val response = plurkOAuth.sendRequest(
        "/APP/Timeline/markAsRead", Verb.POST, 
        "ids" -> plurkIDs.mkString("[", ",", "]")
      )

      response.map { jsonData => 
        jsonData.get[String]("success_text") == "ok"
      }
    }

    def uploadPicture(file: File): Try[(String, String)] = {
      val response = plurkOAuth.uploadFile("/APP/Timeline/uploadPicture", "image", file)
      response.map { jsonData =>
        (jsonData.get("full"), jsonData.get("thumbnail"))
      }
    }
  }
}

