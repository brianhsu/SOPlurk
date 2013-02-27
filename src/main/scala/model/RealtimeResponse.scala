package org.bone.soplurk.model

import net.liftweb.json.JsonAST._

/**
 *  Response from Realtime API.
 *
 *  @param  user      Who posted a new response?
 *  @param  plurk     Which plurk does this response belongs to?
 *  @param  response  Content of this response.
 */
case class RealtimeResponse(user: User, plurk: Plurk, response: Response)

object RealtimeResponse {

  import MyJValueImplicits._

  def apply(jsonData: JValue): RealtimeResponse = {
    
    val response = Response(jsonData \ "response")
    val user = User(jsonData \\ response.userID.toString)
    val plurk = Plurk(jsonData \ "plurk")

    RealtimeResponse(user, plurk, response)
  }

}

