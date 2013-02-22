package org.bone.soplurk.model

import net.liftweb.json.JValue

/**
 *  Represented Plurk entry returned by PlurkTop
 *
 *  @param  plurk       The content of this plurk.
 *  @param  voteUp      How many user voted up this plurk.
 *  @param  voteDown    How many user voted down this plurk.
 *  @param  score       Score of this plurk.
 *  @param  topicID     Which topic does this plurk belong to?
 */
case class TopPlurk(plurk: Plurk, voteUp: Int, voteDown: Int, score: Double, topicID: Int)

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
      voteUp = jsonData.get("vote_up"),
      voteDown = jsonData.get("vote_down"),
      score = jsonData.get("score"),
      topicID = jsonData.get("topic_id")
    )

  }

}

