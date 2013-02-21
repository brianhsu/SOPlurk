package org.bone.soplurk.constant

import org.bone.soplurk.exceptions._

/**
 *  Represtend a user's relationship.
 *
 *  @param  word    The relationship value returned by Plurk's JSON API.
 */
sealed abstract class Relationship(val word: String)

/**
 *  Represtend a user's relationship.
 *
 */
object Relationship {

  /**
   *  Get corresponding Relationship case object according to code.
   *
   *  @param    word    relationship value returned by Plurk's JSON API.
   *  @return           Corresponding case object.
   *  @throws           NoSuchRelationshipException if no matched code.
   */
  def apply(word: String): Relationship = word match {
    case "not_saying"            => NotSaying
    case "single"                => Single
    case "married"               => Married
    case "divorced"              => Divorced
    case "engaged"               => Engaged
    case "in_relationship"       => InRelationship
    case "complicated"           => Complicated
    case "widowed"               => Widowed
    case "unstable_relationship" => UnstableRelationship
    case "open_relationship"     => OpenRelationship
    case _ => throw new NoSuchRelationshipException(word)
  }

  case object NotSaying extends Relationship("not_saying")
  case object Single extends Relationship("single")
  case object Married extends Relationship("married")
  case object Divorced extends Relationship("divorced")
  case object Engaged extends Relationship("engaged")
  case object InRelationship extends Relationship("in_relationship")
  case object Complicated extends Relationship("complicated")
  case object Widowed extends Relationship("widowed")
  case object UnstableRelationship extends Relationship("unstable_relationship")
  case object OpenRelationship extends Relationship("open_relationship")
}

