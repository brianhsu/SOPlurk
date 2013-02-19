package org.bone.soplurk.model

import scala.util.Try
import net.liftweb.json.JsonAST._

/**
 *  This class holds information about whether a plurk is replurkable or has been
 *  replurked.
 *
 *  @param  isReplurkable        Is this plruk replurkable?
 *  @param  isReplurked          Did current user replurked this plruk?
 *  @param  replurkersCount      How many users replurked this plurk?
 *  @param  replurkerID          ID of a user who has replurked given plurk to current user's timeline.
 *  @param  replurkers           List of ids of users who replurked given plurk (can be truncated).
 */
case class ReplurkInfo (
  isReplurkable: Boolean,
  isReplurked: Boolean,
  replurkersCount: Int,
  replurkerID: Option[Long],
  replurkers: Option[List[Long]]
)

object ReplurkInfo {

  import MyJValueImplicits._

  /**
   *  Create ReplurkInfo from Plurk JSON object
   */
  def apply(plurk: JValue) = new ReplurkInfo (
    isReplurkable = plurk.get("replurkable"),
    isReplurked = plurk.get("replurked"),
    replurkersCount = plurk.get("replurkers_count"),
    replurkerID = Try(plurk.getOption[Long]("replurker_id")).getOrElse(None),
    replurkers = plurk.getOption[List[Long]]("replurkers").filterNot(_.isEmpty)
  )
}

