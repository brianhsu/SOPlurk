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
     *
     *  @return               Responses of this plurk.
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

    /**
     *  Add response to a Plurk.
     *
     *  @param  plurkID     Which plurk you want to response.
     *  @param  content     Content of your response.
     *  @param  qualifier   Qualifier of this response.
     *
     *  @return             Success[Response] which contains your response if everything is ok.
     */
    def responseAdd(plurkID: Long, content: String, 
                    qualifier: Qualifier): Try[Response] = {

      val response = plurkOAuth.sendRequest(
        "/APP/Responses/responseAdd", Verb.POST,
        "plurk_id"  -> plurkID.toString,
        "content"   -> content,
        "qualifier" -> qualifier.name
      )

      response.map { jsonData => Response(jsonData) }

    }

    /**
     *  Delete response of a plurk.
     *
     *  @param  plurkID     Which plurk does that response belongs to?
     *  @param  responseID  The unique id of that response you want to delete.
     *
     *  @return             Success[Boolean](true) if delete successfully.
     */
    def responseDelete(plurkID: Long, responseID: Long): Try[Boolean] = {

      val response = plurkOAuth.sendRequest(
        "/APP/Responses/responseDelete", Verb.POST,
        "plurk_id"    -> plurkID.toString,
        "response_id" -> responseID.toString
      )

      response.map { jsonData => jsonData.get[String]("success_text") == "ok" }
    }

  }

}

