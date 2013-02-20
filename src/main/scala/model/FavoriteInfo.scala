package org.bone.soplurk.model

import net.liftweb.json.JsonAST._

/**
 *  This class holds information about whether a Plurk is current user's favorite or
 *  is favorited by others.
 *
 *  @param  isFavorite      True if current user has liked given plurk.
 *  @param  favoriteCount   Number of users who liked given plurk.
 *  @param  favorers        List of ids of users who liked given plurk (can be truncated).
 */
case class FavoriteInfo(
  isFavorite: Boolean,
  favoriteCount: Int,
  favorers: Option[List[Long]]
)

object FavoriteInfo {

  import MyJValueImplicits._

  /**
   *  Create FavoriteInfo from Plurk JSON object
   */
  def apply(plurk: JValue) = new FavoriteInfo (
    isFavorite = plurk.get("favorite"),
    favoriteCount = plurk.get("favorite_count"),
    favorers = plurk.getOption[List[Long]]("favorers").filterNot(_.isEmpty)
  )
}

