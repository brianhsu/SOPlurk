package org.bone.soplurk.api

import org.bone.soplurk.model._
import org.bone.soplurk.api.PlurkAPI._
import org.bone.soplurk.api.TimelineParser._

import org.scribe.model.Verb

import scala.util.Try
import java.util.Date

trait Responses {

  this: PlurkAPI =>

  import MyJValueImplicits._

  /**
   *  API of /APP/Responses/
   */
  object Responses {

    import TimelineParser._

    /**
     *  Get responses from a Plurk.
     *
     *  @param  plurkID       The unique id of plurk that you want to fetch responses.
     *  @param  fromResponse  Only fetch responses an offset, index starts from 0.
     */
    def get(plurkID: Long, fromResponse: Int = 0): Try[PlurkResponses] = {
      
      val params = List(
        "plurk_id" -> plurkID.toString,
        "from_response" -> fromResponse.toString
      ).filterNot(_ == ("from_response", "0"))

      val response = plurkOAuth.sendRequest("/APP/Responses/get", Verb.GET, params: _*)

      response.map { jsonData =>
        
        PlurkResponses(
          friends   = parseUsersMap(jsonData \ "friends"),
          responses = (jsonData \ "responses").children.map(x => Response(x)),
          seen      = jsonData.get("responses_seen")
        )
      }
    }

  }

}

