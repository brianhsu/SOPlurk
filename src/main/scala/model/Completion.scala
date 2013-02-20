package org.bone.soplurk.model

import net.liftweb.json.JValue

import java.util.Date
import java.util.Locale
import java.text.SimpleDateFormat

/**
 *  Represented as a user's friend completion entry.
 *
 *  @param  nickname      The unique nickname of that user.
 *  @param  fullName      Full name of that user.
 *  @param  displayName   Display name of that user, if is iet.
 */
case class Completion(nickname: String, fullName: String, displayName: Option[String])

object Completion {

  import MyJValueImplicits._

  /**
   *  Create Completion object from JSON dta.
   *
   *  @param  jsonData  JSON returned by Plurk.
   *  @return           Completion object corresponding to jsonData.
   */
  def apply(jsonData: JValue): Completion = {
    
    Completion(
      nickname = jsonData.get("nick_name"),
      fullName = jsonData.get("full_name"),
      displayName = jsonData.getOption[String]("display_name")
    )

  }

}

