package org.bone.splurk2.model

import java.util.Date
import java.lang.{Long => JLong}
import net.liftweb.json.JsonAST._

/**
 *  Minmial info about a Plurk post
 *
 *  @param  plurkID     Plurk ID
 *  @param  onwerID     Who posted this plurk?
 *  @param  userID      In which users timeline?
 *  @param  noComments  ture if it's commetable, otherwise false
 */
case class Plurk(
  plurkID: Long, onwerID: Long, userID: Long, 
  qualifier: Qualifier, contents: String,
  plurkType: PlurkType, readStatus: ReadStatus, 
  noComments: Boolean, 
  posted: Date, 
  language: String,
  responseCount: Int,
  replurkInfo: ReplurkInfo,
  favoriteInfo: FavoriteInfo,
  qualifierTranslated: Option[String],
  limitedTo: Option[List[Long]],
  responseSeen: Option[Int],
  contentRaw: Option[String]
) {

  def plurkURL = "http://plurk.com/p/" + JLong.toString(plurkID, 36)
}
