package org.bone.soplurk.api

import org.bone.soplurk.model._
import org.bone.soplurk.api.PlurkAPI._
import org.bone.soplurk.api.TimelineParser._

import org.scribe.model.Verb

import scala.util.Try
import java.util.Date

trait PlurkSearch {

  this: PlurkAPI =>

  import MyJValueImplicits._

  /**
   *  API of /APP/PlurkSearch/
   */
  object PlurkSearch {

    import TimelineParser._

    /**
     *  Return lastet 20 plurks on a search term.
     *
     *  Note:
     *
     *  This method's `offset` is not index, it's a `plurkID`.
     *
     *  If you want fetch the second page of search result, you should
     *  provide `offset` field as the `lastOffset` value returned in the 
     *  previous `PlurkSearchResult` object.
     *
     *  @param  query   The query after plurks.
     *  @param  offset  A `plurkID` of the oldest Plurk in the last search result.
     */
    def search(query: String, offset: Int = 0): Try[PlurkSearchResult] = {
      
      val params = List(
        "query"  -> query,
        "offset" -> offset.toString
      ).filterNot(_ == ("offset", "0"))
      
      val response = plurkOAuth.sendRequest(
        "/APP/PlurkSearch/search", Verb.GET, params: _*
      )

      response.map { jsonData =>
        PlurkSearchResult(
          users      = parseUsersMap(jsonData \ "users"),
          plurks     = parsePlurks(jsonData \ "plurks"),
          hasMore    = jsonData.get("has_more"),
          lastOffset = jsonData.get("last_offset")
        )
      }
    }

  }

}

