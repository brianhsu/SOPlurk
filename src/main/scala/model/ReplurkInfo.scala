package org.bone.splurk2.model

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
    isReplurkable = (plurk \ "replurkable").get[Boolean],
    isReplurked = (plurk \ "replurked").get[Boolean],
    replurkersCount = (plurk \ "replurkers_count").get[BigInt].toInt,
    replurkerID = (plurk \ "replurker_id").getOpt[BigInt].map(_.toLong),
    replurkers = (plurk \ "replurkers").getOpt[List[Long]].filterNot(_.isEmpty)
  )
}

