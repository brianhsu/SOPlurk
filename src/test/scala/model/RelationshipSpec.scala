package org.bone.splurk2.model

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

import org.bone.splurk2.exceptions._
import org.bone.splurk2.model._
import org.bone.splurk2.model.Relationship._

class RelationshipSpec extends FunSpec with ShouldMatchers {

  describe("A Relationship") {

    it ("should have correct relationship value") {
      NotSaying.word            should be === "not_saying"           
      Single.word               should be === "single"               
      Married.word              should be === "married"              
      Divorced.word             should be === "divorced"             
      Engaged.word              should be === "engaged"              
      InRelationship.word       should be === "in_relationship"      
      Complicated.word          should be === "complicated"          
      Widowed.word              should be === "widowed"              
      UnstableRelationship.word should be === "unstable_relationship"
      OpenRelationship.word     should be === "open_relationship"    
    }

    it ("should able to prase status code to case object") {
      Relationship("not_saying")            should be === NotSaying
      Relationship("single")                should be === Single
      Relationship("married")               should be === Married
      Relationship("divorced")              should be === Divorced
      Relationship("engaged")               should be === Engaged
      Relationship("in_relationship")       should be === InRelationship
      Relationship("complicated")           should be === Complicated
      Relationship("widowed")               should be === Widowed
      Relationship("unstable_relationship") should be === UnstableRelationship
      Relationship("open_relationship")     should be === OpenRelationship
    }

    it ("should throw NoRelationshipException at unknown code") {
      val exception = intercept[NoSuchRelationshipException] {
        Relationship("NotDefinedRelationship")
      }
      exception.word should be === "NotDefinedRelationship"
    }
  }
}

