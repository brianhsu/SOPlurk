package org.bone.splurk2.model

import net.liftweb.json.JsonAST._
import java.util.Date

/**
 *  Represnted a user's basic information.
 *
 *  @param  id                  The unique user id.
 *  @param  nickname            The unique nick_name of the user.
 *  @param  displayName         The non-unique display name of the user.
 *  @param  isVerifiedAccount   Is this user a [[http://www.plurk.com/Verify/ Plurk verified account]]?
 *  @param  gender              The user's gender
 *  @param  karma               User's karma value
 *  @param  hasProfileImage     Does this user have profile image?
 *  @param  birthdayPrivacy     User's birthday privacy setting.
 *  @param  defaultLanguage     User's default language.
 *  @param  avatarVersion       The user's avatar version.
 *  @param  location            The user's location.
 *  @param  birthday            The user's birthday, if birthday privact is set to HideYear, year will be 1904.
 *  @param  timezone            The user's timezone, None if user has set it to browser's default.
 *  @param  nameColor           The user's name color in HTML hex color value.
 */
case class User (
  id: Long,
  nickname: String,
  fullName: String,
  displayName: String,
  isVerifiedAccount: Boolean,
  gender: Gender,
  karma: Double,
  hasProfileImage: Boolean,
  birthdayPrivacy: BirthdayPrivacy,
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
    case true  => s"http://avatars.plurk.com/$id-small${avatarVersion.getOrElse("")}.gif"
    case false => "http://www.plurk.com/static/default_small.gif"
  }

  /**
   *  User's small medium avatar URL.
   */
  val mediumAvatar = hasProfileImage match {
    case true  => s"http://avatars.plurk.com/$id-medium${avatarVersion.getOrElse("")}.gif"
    case false => "http://www.plurk.com/static/default_medium.gif"
  }

  /**
   *  User's big medium avatar URL.
   */
  val bigAvatar = hasProfileImage match {
    case true  => s"http://avatars.plurk.com/$id-big${avatarVersion.getOrElse("")}.jpg"
    case false => "http://www.plurk.com/static/default_big.gif"
  }

}

object User {

  import MyJValueImplicits._
  import java.text.SimpleDateFormat
  import java.util.Locale

  private lazy val dateFormatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US)
  private def toDate(dateString: String): Date = dateFormatter.parse(dateString)
  
  /**
   *  Create BasicUser object from Plurk's user data JSON.
   *
   *  @param    user    The user JSON data returned by Plurk.
   */
  def apply(user: JValue) = new User (
    id = user.get[BigInt]("id").toLong,
    nickname = user.get("nick_name"),
    fullName = user.get("full_name"),
    displayName = user.get("display_name"),
    isVerifiedAccount = user.get("verified_account"),
    gender = Gender(user.get[BigInt]("gender").toByte),
    karma = user.get[Double]("karma"),
    hasProfileImage = (user.get[BigInt]("has_profile_image") == 1),
    birthdayPrivacy = BirthdayPrivacy(user.get[BigInt]("bday_privacy").toByte),
    defaultLanguage = user.get("default_lang"),
    avatarVersion = user.getOption[BigInt]("avatar").map(_.toInt),
    location = user.getOption("location"),
    birthday = user.getOption("date_of_birth").map(toDate),
    timezone = user.getOption("timezone"),
    nameColor = user.getOption("name_color")
  )
}

