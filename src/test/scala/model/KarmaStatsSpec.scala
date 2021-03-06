package org.bone.soplurk.model

import org.bone.soplurk.exceptions._
import org.bone.soplurk.model._

import org.scalatest.FunSpec
import org.scalatest.Matchers

import java.util.Date
import net.liftweb.json._

class KarmaStatsSpec extends FunSpec with Matchers {

  import KarmaStatsSpec._

  describe("A KarmaStats") {

    it ("should able to parse plurk's karma data that without fall reason") {
      val correctStats = new KarmaStats (
        current = 121,
        fallReason = None,
        graphURL = "http://image.example/graph1",
        trend = List(
          "1360080759-120.75",
          "1360109431-120.76",
          "1360138145-120.77"
        )
      )

      KarmaStats(withoutReason) shouldBe correctStats
    }

    it ("should able to parse plurk's karma data that with fall reason") {
      val correctStats = new KarmaStats (
        current = 121.02,
        fallReason = Some("friends_rejections"),
        graphURL = "http://image.example/graph2",
        trend = List(
          "1360080759-120.75",
          "1360109431-120.76",
          "1360138145-120.77"
        )
      )

      KarmaStats(withReason) shouldBe correctStats
    }

  }
}

object KarmaStatsSpec {

  val withoutReason = JsonParser.parse("""{
    "karma_fall_reason": "",
    "current_karma": 121,
    "karma_graph": "http://image.example/graph1",
    "karma_trend": [
        "1360080759-120.75",
        "1360109431-120.76",
        "1360138145-120.77"
    ]
  }""")

  val withReason = JsonParser.parse("""{
    "karma_fall_reason": "friends_rejections",
    "current_karma": 121.02,
    "karma_graph": "http://image.example/graph2",
    "karma_trend": [
        "1360080759-120.75",
        "1360109431-120.76",
        "1360138145-120.77"
    ]
  }""")

}
