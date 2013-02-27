package org.bone.soplurk.api

import org.bone.soplurk.constant._
import org.bone.soplurk.model._
import org.bone.soplurk.api.PlurkAPI._
import org.bone.soplurk.api.TimelineParser._

import org.scribe.model.Verb

import net.liftweb.json.JsonAST._
import scala.util.Try

trait PlurkTop {

  this: PlurkAPI =>

  import MyJValueImplicits._

  /**
   *  API of /APP/PlurkTop/
   */
  object PlurkTop {
    
    /**
     *  Get collections of PlurkTop.
     *
     *  @return   Success[List[Collection]] if get data from Plurk successfully.
     */
    def getCollections: Try[List[Collection]] = {
      val response = plurkOAuth.sendRequest("/APP/PlurkTop/getCollections", Verb.GET)

      response.map { jsonData => 
        jsonData.children.map(c => Collection(c)) 
      }
    }

    /**
     *  Get topics of PlurkTop.
     *
     *  @param  language  Get topics for this specified language.
     *  @return           Success[List[Topic]] if get data from Plurk correctly.
     */
    def getTopics(language: String = "en"): Try[List[Topic]] = {

      val params = List("lang" -> language).filterNot(_._2 == "en")
      val response = plurkOAuth.sendRequest(
        "/APP/PlurkTop/getTopics", Verb.GET, params: _*
      )

      response.map { jsonData => 
        jsonData.children.map(c => Topic(c)) 
      }
    }

    /**
     *  Get plurks from PlurkTop.
     *
     *  The offset is the double value stored in `PlurkTopResult.offset`.
     *
     *  For example, if you get the first page by the following code:
     *
     *  {{{
     *
     *    val Success(firstPage) = plurkAPI.getPlurks("Taiwan")
     *
     *  }}}
     *
     *  You need to do the following to get the second page:
     *
     *  {{{
     *
     *    val Success(secondPage) = plurkAPI.getPlurks(
     *      collectionName = "Taiwan", 
     *      offset = Some(firstPage.offset)
     *    )
     *
     *  }}}
     *
     *
     *  @param  collectionName    The collection you want to fetch in English name.
     *  @param  limit             How many plurks will be returned?
     *  @param  offset            Offset of page.
     *  @param  sorting           Sorting plurks by what? (Default is sorted by Hot)
     *  @param  topicIDFilter     Filter only plurks with this topicID.
     */
    def getPlurks(collectionName: String, limit: Int = 30, 
                  offset: Option[Double] = None,
                  sorting: Option[Sorting] = None,
                  topicIDFilter: Option[Int] = None): Try[PlurkTopResult] = {

      val params = List("collection_name" -> collectionName)
      val optionalParams = List(
        Option(limit).filterNot(_ == 30).map("limit" -> _.toString),
        offset.map("offset" -> _.toString),
        sorting.map("sorting" -> _.word),
        topicIDFilter.map("topic_filter" -> _.toString)
      ).flatten

      val response = plurkOAuth.sendRequest(
        "/APP/PlurkTop/getPlurks", Verb.GET,
        (params ++ optionalParams):_*
      )

      response.map { jsonData =>
        
        PlurkTopResult(
          users = parseUsersMap(jsonData \ "plurk_users"),
          plurks = (jsonData \ "plurks").children.map(p => TopPlurk(p)),
          offset = jsonData.get("offset")
        )

      }

    }

  }

}

