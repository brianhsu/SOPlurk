package org.bone.soplurk.model

import org.bone.soplurk.constant._
import net.liftweb.json.JsonAST._
import java.util.Date

/**
 *  Represnted a user's basic information.
 *
 *  @param  id                  The unique user id.
 *  @param  nickname            The unique nick_name of the user.
 *  @param  displayName         The non-unique display name of the user.
 *  @param  isVerifiedAccount   Is this user a 
 *                              [[http://www.plurk.com/Verify/ Plurk verified account]]?
 *
 *  @param  gender              The user's gender
 *  @param  karma               User's karma value
 *  @param  hasProfileImage     Does this user have profile image?
 *  @param  birthdayPrivacy     User's birthday privacy setting.
 *  @param  defaultLanguage     User's default language.
 *  @param  avatarVersion       The user's avatar version.
 *  @param  location            The user's location.
 *  @param  birthday            The user's birthday, if birthday privact is set to 
 *                              HideYear, year will be 1904.
 *
 *  @param  timezone            The user's timezone, None if user has set it to 
 *                              browser's default.
 *
 *  @param  nameColor           The user's name color in HTML hex color value.
 */
case class User (
  id: Long,
  nickname: String,
  fullName: String,
  displayName: Option[String],
  isVerifiedAccount: Boolean,
  gender: Gender,
  karma: Double,
  hasProfileImage: Boolean,
  birthdayPrivacy: Option[BirthdayPrivacy],
  defaultLanguage: String,
  avatarVersion: Option[Int],
  location: Option[String],
  birthday: Option[Date],
  timezone: Option[String],
  nameColor: Option[String]
) {
  
  /**
   *  User's small avatar URL.
   */
  val smallAvatar = hasProfileImage match {
    case true  => s"http://avatars.plurk.com/$id-small${avatarVersion.filter(_ != 0).getOrElse("")}.gif"
    case false => "http://www.plurk.com/static/default_small.gif"
  }

  /**
   *  User's small medium avatar URL.
   */
  val mediumAvatar = hasProfileImage match {
    case true  => s"http://avatars.plurk.com/$id-medium${avatarVersion.filter(_ != 0).getOrElse("")}.gif"
    case false => "http://www.plurk.com/static/default_medium.gif"
  }

  /**
   *  User's big medium avatar URL.
   */
  val bigAvatar = hasProfileImage match {
    case true  => s"http://avatars.plurk.com/$id-big${avatarVersion.filter(_ != 0).getOrElse("")}.jpg"
    case false => "http://www.plurk.com/static/default_big.gif"
  }

}

object User {

  import MyJValueImplicits._
  import org.bone.soplurk.util.DateTimeUtils

  /**
   *  Create BasicUser object from Plurk's user data JSON.
   *
   *  @param    user    The user JSON data returned by Plurk.
   */
  def apply(user: JValue) = new User (
    id = user.get("id"),
    nickname = user.get("nick_name"),
    fullName = user.get("full_name"),
    displayName = user.getOption("display_name"),
    isVerifiedAccount = user.get("verified_account"),
    gender = Gender(user.get[Int]("gender").toByte),
    karma = user.get("karma"),
    hasProfileImage = (user.get[Int]("has_profile_image") == 1),
    birthdayPrivacy = user.getOption[Int]("bday_privacy").map(x => BirthdayPrivacy(x.toByte)),
    defaultLanguage = user.get("default_lang"),
    avatarVersion = user.getOption("avatar"),
    location = user.getOption("location"),
    birthday = user.getOption[String]("date_of_birth").map(DateTimeUtils.fromPlurkDate),
    timezone = user.getOption("timezone"),
    nameColor = user.getOption("name_color")
  )
}

