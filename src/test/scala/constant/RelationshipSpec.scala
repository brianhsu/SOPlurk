package org.bone.soplurk.constant

import org.scalatest.FunSpec
import org.scalatest.Matchers

import org.bone.soplurk.constant._
import org.bone.soplurk.constant.Relationship._
import org.bone.soplurk.exceptions._
import org.bone.soplurk.model._

class RelationshipSpec extends FunSpec with Matchers {

  describe("A Relationship") {

    it ("should have correct relationship value") {
      NotSaying.word            shouldBe "not_saying"           
      Single.word               shouldBe "single"               
      Married.word              shouldBe "married"              
      Divorced.word             shouldBe "divorced"             
      Engaged.word              shouldBe "engaged"              
      InRelationship.word       shouldBe "in_relationship"      
      Complicated.word          shouldBe "complicated"          
      Widowed.word              shouldBe "widowed"              
      UnstableRelationship.word shouldBe "unstable_relationship"
      OpenRelationship.word     shouldBe "open_relationship"    
    }

    it ("should able to prase status code to case object") {
      Relationship("not_saying")            shouldBe NotSaying
      Relationship("single")                shouldBe Single
      Relationship("married")               shouldBe Married
      Relationship("divorced")              shouldBe Divorced
      Relationship("engaged")               shouldBe Engaged
      Relationship("in_relationship")       shouldBe InRelationship
      Relationship("complicated")           shouldBe Complicated
      Relationship("widowed")               shouldBe Widowed
      Relationship("unstable_relationship") shouldBe UnstableRelationship
      Relationship("open_relationship")     shouldBe OpenRelationship
    }

    it ("should throw NoRelationshipException at unknown code") {
      val exception = intercept[NoSuchRelationshipException] {
        Relationship("NotDefinedRelationship")
      }
      exception.word shouldBe "NotDefinedRelationship"
    }
  }
}

