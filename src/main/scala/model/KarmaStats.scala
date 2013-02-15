package org.bone.splurk2.model

import net.liftweb.json.JsonAST._

/**
 *  Represented Plurk Karam status
 *
 *  trends is a List conatins String which is in following format:
 *
 *
 *    [unixtimestamp]-[karma_value]
 *
 *  For example:
 *
 *  {{{
 *    List(
 *      "1360080759-120.75",
 *      "1360109431-120.76",
 *      "1360138145-120.77",
 *      "1360167177-120.78"
 *    )
 *  }}}
 *
 *  @param  current     User's current karama value.
 *  @param  fallReason  The falling reason about karma.
 *  @param  graphURL    The url of karma trend graph.
 *  @param  trend       Trends about karma change.
 */
case class KarmaStats(
  current: Double, fallReason: Option[String],
  graphURL: String, trend: List[String]
)

object KarmaStats {

  import MyJValueImplicits._

  /**
   *  Generate KaramStats from JSON data.
   *
   *  @param    karamStats      JSON data returned from Plurk API.
   *  @return                   KarmaStats object corresponding to JSON data.
   */
  def apply(karmaStats: JValue) = new KarmaStats (
    current = karmaStats.get[Double]("current_karma"),
    fallReason = karmaStats.getOption[String]("karma_fall_reason").filterNot(_.isEmpty),
    graphURL = karmaStats.get("karma_graph"),
    trend = karmaStats.get[List[String]]("karma_trend")
  )
}

