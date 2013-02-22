package org.bone.soplurk.api

import org.bone.soplurk.constant._
import org.bone.soplurk.model._

import org.scribe.model.Verb

import java.util.Date
import java.io.File

import scala.util.Try

trait Users {

  this: PlurkAPI =>

  import MyJValueImplicits._
  import java.text.SimpleDateFormat

  /**
   *  API of /APP/Users/
   */
  object Users {

    private def isValueDefined[T](tuple: (String, Option[T])) = tuple._2.isDefined

    /**
     *  Convert date to yyyy-MM-dd format string
     */
    private def toBirthdayString(date: Date) = {
      val dateFormatter = new SimpleDateFormat("yyyy-MM-dd")
      dateFormatter.format(date)
    }

    /**
     *  Update user's information.
     *
     *  @param  fulleName       User's full name.
     *  @param  email           User's email.
     *  @param  displayName     User's display name, can be empty string, must fewer than 15 characters.
     *  @param  privacy         User's timeline privacy setting.
     *  @param  birthday        User's birthday.
     */
    def update(fullName: Option[String] = None, 
               email: Option[String] = None,
               displayName: Option[String] = None,
               privacy: Option[TimelinePrivacy] = None,
               birthday: Option[Date] = None): Try[Boolean] = {

      val params = Map(
        "full_name" -> fullName, 
        "email" -> email,
        "display_name" -> displayName,
        "privacy" -> privacy.map(_.word),
        "date_of_birth" -> birthday.map(toBirthdayString)
      ).filter(isValueDefined).mapValues(_.get).toSeq

      val jsonData = plurkOAuth.sendRequest("/APP/Users/update", Verb.POST, params:_*)

      jsonData.map { data =>
        data.get[String]("success_text") == "ok" 
      }
    }

    /**
     *  Get current user's information.
     *
     *  {{{
     *    val currUser = plurkAPI.User.currUser
     *
     *    for ((extUserInfo, pageTitle, about) <- currUser) {
     *      println(userInfo)   // Extended user information about current user.
     *      println(pageTitle)  // Page title of current user.
     *      println(about)      // About text of current user.
     *    }
     *
     *  }}}
     *
     *  @return     Success((extUserInfo, pageTitle, about)) if successful.
     */
    def currUser: Try[(ExtendedUser, String, String)]  = {

      val response = plurkOAuth.sendRequest("/APP/Users/currUser", Verb.GET)

      response.map { jsonData =>
        val extendedUser = ExtendedUser(jsonData)
        val pageTitle = jsonData.getOption[String]("page_title").getOrElse("")
        val about = jsonData.getOption[String]("about").getOrElse("")

        (extendedUser, pageTitle, about)
      }

    }

    /**
     *  Get current user's karma stats.
     *
     *  @return     Current user's karma stats if successful.
     */
    def getKarmaStats: Try[KarmaStats] = {
      val response = plurkOAuth.sendRequest("/APP/Users/getKarmaStats", Verb.GET)
      response.map( jsonData => KarmaStats(jsonData))
    }

    /**
     *  Update User's avatar image.
     *
     *  According to Plurk API document, the picture will be scaled down to 3 versions: big, medium and small. 
     *  The optimal size of profile_image should be 195x195 pixels. 
     *
     *  @param  file    The new image file of avatar.
     */
    def updatePicture(file: File): Try[User] = {
      val response = plurkOAuth.uploadFile("/APP/Users/updatePicture", "profile_image", file)
      response.map { jsonData => User(jsonData) }
    }
  }

}

