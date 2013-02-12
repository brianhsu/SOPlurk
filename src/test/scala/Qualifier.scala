package org.bone.splurk2.test.model

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

import org.bone.splurk2.exceptions.NoSuchQualifierException
import org.bone.splurk2.model._

class QualifierSpec extends FunSpec with ShouldMatchers {

  describe("A Qualifier") {

    it ("should able to get it's english name correctly") {

      List(Loves, Likes).map(_.name) should be === List("loves", "likes")
      List(Shares, Gives).map(_.name) should be === List("shares", "gives")
      List(Hates, Wants).map(_.name) should be === List("hates", "wants")
      List(Has, Will).map(_.name) should be === List("has", "will")
      List(Asks, Wishes).map(_.name) should be === List("asks", "wishes")
      List(Was, Feels).map(_.name) should be === List("was", "feels")
      List(Thinks, Says).map(_.name) should be === List("thinks", "says")
      List(Is, ::).map(_.name) should be === List("is", ":")
      List(FreeStyle, Hopes).map(_.name) should be === List("freestyle", "hopes")
      List(Needs, Wonders).map(_.name) should be === List("needs", "wonders")

    }

    it ("should able to prase english name to Qualifier case object") {

      def create = Qualifier.apply _

      List("loves", "likes").map(create) should be === List(Loves, Likes)
      List("shares", "gives").map(create) should be === List(Shares, Gives)
      List("hates", "wants").map(create) should be === List(Hates, Wants)
      List("has", "will").map(create) should be === List(Has, Will)
      List("asks", "wishes").map(create) should be === List(Asks, Wishes)
      List("was", "feels").map(create) should be === List(Was, Feels)
      List("thinks", "says").map(create) should be === List(Thinks, Says)
      List("is", ":").map(create) should be === List(Is, ::)
      List("freestyle", "hopes").map(create) should be === List(FreeStyle, Hopes)
      List("needs", "wonders").map(create) should be === List(Needs, Wonders)

    }

    it ("should throw NoSuchQualifier exception when no such english qualifier") {
      val exception = intercept[NoSuchQualifierException] {
        Qualifier("NoSuchQualifierInPlurk")
      }
      exception.qualifier should be === "NoSuchQualifierInPlurk"
    }
  }
}

