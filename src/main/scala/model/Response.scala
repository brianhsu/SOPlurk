package org.bone.soplurk.model

import org.bone.soplurk.constant._

import net.liftweb.json.JValue

import java.util.Date
import java.util.Locale
import java.text.SimpleDateFormat

/**
 *  Represented Response of a Plurk.
 *
 *  @param  userID      The response author's user id.
 *  @param  plurkID     Which plurk does this response belong?
 *  @param  id          The unique ID of this response.
 *  @param  content     Content of this response.
 *  @param  contentRaw  The raw content of this response, without any URL replacing...etc.
 *  @param  qualifier   Qualifier of this response.
 *  @param  posted      When did this response poeted?
 *  @param  language    Language code of this response.
 */
case class Response(
  userID: Long, plurkID: Long, id: Long, 
  content: String, contentRaw: String,
  qualifier: Qualifier,
  posted: Date,
  language: String
)

object Response {

  import org.bone.soplurk.util.DateTimeUtils
  import MyJValueImplicits._


  /**
   *  Create Response object from JSON returned by Plurk.
   *
   *  @param    jsonData  JSON data of response returned by Plurk.
   *  @return             Response object corresponding to the jsonData.
   */
  def apply(jsonData: JValue): Response = {
    Response(
      userID = jsonData.get("user_id"),
      plurkID = jsonData.get("plurk_id"),
      id = jsonData.get("id"),
      content = jsonData.get("content"),
      contentRaw = jsonData.get("content_raw"),
      qualifier = Qualifier(jsonData.get("qualifier")),
      posted = DateTimeUtils.fromPlurkDate(jsonData.get("posted")),
      language = jsonData.get("lang")
    )
  }

}

