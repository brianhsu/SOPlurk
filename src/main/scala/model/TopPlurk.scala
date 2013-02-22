package org.bone.soplurk.model

import net.liftweb.json.JValue
import java.util.Date

/**
 *  Represented Plurk entry returned by PlurkTop
 *
 *  @param  plurk       The content of this plurk.
 *  @param  voteUp      How many user voted up this plurk.
 *  @param  voteDown    How many user voted down this plurk.
 *  @param  score       Score of this plurk.
 *  @param  topicID     Which topic does this plurk belong to?
 *  @param  posterUID   How posted it to PlurkTop?
 *  @param  posted      When did this plurk posted to PlurkTop.
 */
case class TopPlurk(
  plurk: Plurk, voteUp: Int, voteDown: Int, 
  score: Double, topicID: Int, posterUID: Int,
  posted: Date
)

object TopPlurk {

  import MyJValueImplicits._

  /**
   *  Construct TopPlurk object from JSON returned by Plurk.
   *
   *  @param    jsonData    The plurk entry JSON returned by PlurkTop API.
   *  @return               The corresponding TopPlurk object.
   */
  def apply(jsonData: JValue): TopPlurk = {
    
    TopPlurk(
      plurk = Plurk(jsonData),
      voteUp = jsonData.getOption[Int]("vote_up").getOrElse(0),
      voteDown = jsonData.getOption[Int]("vote_down").getOrElse(0),
      score = jsonData.get("score"),
      topicID = jsonData.get("topic_id"),
      posterUID = jsonData.get("poster_uid"),
      posted = new Date(jsonData.get[Long]("entry_posted") * 1000)
    )

  }

}

