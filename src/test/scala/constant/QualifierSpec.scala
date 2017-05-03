package org.bone.soplurk.constant

import org.scalatest.FunSpec
import org.scalatest.Matchers

import org.bone.soplurk.constant._
import org.bone.soplurk.constant.Qualifier._
import org.bone.soplurk.model._

class QualifierSpec extends FunSpec with Matchers {

  describe("A Qualifier") {

    it ("should able to get it's english name correctly") {

      List(Loves, Likes).map(_.name) shouldBe List("loves", "likes")
      List(Shares, Gives).map(_.name) shouldBe List("shares", "gives")
      List(Hates, Wants).map(_.name) shouldBe List("hates", "wants")
      List(Has, Will).map(_.name) shouldBe List("has", "will")
      List(Asks, Wishes).map(_.name) shouldBe List("asks", "wishes")
      List(Was, Feels).map(_.name) shouldBe List("was", "feels")
      List(Thinks, Says).map(_.name) shouldBe List("thinks", "says")
      List(Is, ::).map(_.name) shouldBe List("is", ":")
      List(FreeStyle, Hopes).map(_.name) shouldBe List("freestyle", "hopes")
      List(Needs, Wonders).map(_.name) shouldBe List("needs", "wonders")
      List(Whispers).map(_.name) shouldBe List("whispers")

    }

    it ("should able to prase english name to Qualifier case object") {

      def create = Qualifier.apply _

      List("loves", "likes").map(create) shouldBe List(Loves, Likes)
      List("shares", "gives").map(create) shouldBe List(Shares, Gives)
      List("hates", "wants").map(create) shouldBe List(Hates, Wants)
      List("has", "will").map(create) shouldBe List(Has, Will)
      List("asks", "wishes").map(create) shouldBe List(Asks, Wishes)
      List("was", "feels").map(create) shouldBe List(Was, Feels)
      List("thinks", "says").map(create) shouldBe List(Thinks, Says)
      List("is", ":").map(create) shouldBe List(Is, ::)
      List("freestyle", "hopes").map(create) shouldBe List(FreeStyle, Hopes)
      List("needs", "wonders").map(create) shouldBe List(Needs, Wonders)
      List("whispers").map(create) shouldBe List(Whispers)

    }

    it ("should get Qualifier(name) subclass when there is unknown qualifier") {
      Qualifier("NoSuchQualifierInPlurk").name shouldBe "NoSuchQualifierInPlurk"
    }
  }
}

