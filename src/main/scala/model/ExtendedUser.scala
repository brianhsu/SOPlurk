package org.bone.soplurk.model

import net.liftweb.json.JsonAST._

/**
 *  Extended User Information
 *
 *  @param  basicInfo       Basic information about this user.
 *  @param  relationship    Relationship status of this user.
 *  @param  recruited       How many friends has the user recruited.
 */
case class ExtendedUser(
  basicInfo: User, 
  relationship: Relationship,
  recruited: Int
)

object ExtendedUser {
  
  import MyJValueImplicits._

  /**
   *  Construct ExtendedUser by Plurk's user data JSON.
   */
  def apply(user: JValue) = new ExtendedUser(
    basicInfo = User(user),
    relationship = Relationship(user.get[String]("relationship")),
    recruited = user.get[BigInt]("recruited").toInt
  )

}
