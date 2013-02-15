package org.bone.splurk2.model

import java.util.Date
import java.lang.{Long => JLong}
import net.liftweb.json.JsonAST._

/**
 *  Minmial info about a Plurk post
 *
 *  @param  plurkID                 Plurk ID
 *  @param  ownerID                 Who posted this plurk?
 *  @param  userID                  In which users timeline?
 *  @param  qualifier               Qualifier of this plurk.
 *  @param  content                 Contents of this plurk.
 *  @param  plurkType               Type of this plurk.
 *  @param  readStatus              Is this plurk read, unread or muted.
 *  @param  whoIsCommentable        Comment policy setting
 *  @param  posted                  The date this plurk was posted.
 *  @param  language                The plurk's language
 *  @param  responseCount           How many comments this plurk has.
 *  @param  replurkInfo             Information about replurk status.
 *  @param  favoriteInfo            Information about favorers.
 *  @param  quakufuerTranslated     Only set if the language is not English, will be the translated qualifier. 
 *  @param  limitedTo               None if this plurk is public, Some(List(0)) if this plurk is limited to friends, List(userID1, userID2, ...) if limited to users have those userID.
 *  @param  responsesSeen           How many of the responses have the user read.
 *  @param  contentRaw              The raw content as user entered it.
 */
case class Plurk(
  plurkID: Long, ownerID: Long, userID: Long, 
  qualifier: Qualifier, content: String,
  plurkType: PlurkType, readStatus: ReadStatus, 
  whoIsCommentable: CommentSetting, 
  posted: Date, 
  language: String,
  responseCount: Int,
  replurkInfo: ReplurkInfo,
  favoriteInfo: FavoriteInfo,
  qualifierTranslated: Option[String],
  limitedTo: Option[List[Long]],
  responsesSeen: Option[Int],
  contentRaw: Option[String]
) {

  /**
   *  Permanent URL of this plurk.
   */
  val plurkURL = Plurk.urlFromID(plurkID)
}

object Plurk {
  
  import MyJValueImplicits._
  import java.text.SimpleDateFormat
  import java.util.Locale

  private def toDate(dateString: String): Date = {
    val dateFormatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US)
    dateFormatter.parse(dateString)
  }

  private def urlFromID(plurkID: Long) = raw"http://www.plurk.com/p/${JLong.toString(plurkID, 36)}"

  /**
   *  Create Plurk object from Plurk JSON data.
   *
   *  @param    plurk   Plurk JSON data.
   *  @return           Plurk object corresponded to JSON data.
   */
  def apply(plurk: JValue): Plurk = new Plurk (
    plurkID = plurk.get[BigInt]("plurk_id").toLong,
    ownerID = plurk.get[BigInt]("owner_id").toLong,
    userID = plurk.get[BigInt]("user_id").toLong,
    qualifier = Qualifier(plurk.get("qualifier")),
    content = plurk.get[String]("content"),
    plurkType = PlurkType(plurk.get[BigInt]("plurk_type").toByte),
    readStatus = ReadStatus(plurk.get[BigInt]("is_unread").toByte),
    whoIsCommentable = CommentSetting(plurk.get[BigInt]("no_comments").toByte),
    posted = toDate(plurk.get("posted")),
    language = plurk.get[String]("lang"),
    responseCount = plurk.get[BigInt]("response_count").toInt,
    replurkInfo = ReplurkInfo(plurk),
    favoriteInfo = FavoriteInfo(plurk),
    qualifierTranslated = plurk.getOption[String]("qualifier_translated"),
    limitedTo = plurk.getOption[List[Long]]("limited_to").filterNot(_.isEmpty),
    responsesSeen = plurk.getOption[BigInt]("responses_seen").map(_.toInt),
    contentRaw = plurk.getOption[String]("content_raw")
  )

}
