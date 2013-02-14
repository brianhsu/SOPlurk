package org.bone.splurk2.model

import net.liftweb.json.JsonAST._

case class ExtendedUser(
  basicInfo: User, 
  relationship: Relationship,
  recruited: Int
)


