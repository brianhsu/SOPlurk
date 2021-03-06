package org.bone.soplurk.model

import org.bone.soplurk.constant._
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
  recruited: Int,
  about: Option[String],
  plurksCount: Option[Int]
)

object ExtendedUser {
  
  import MyJValueImplicits._

  /**
   *  Construct ExtendedUser by Plurk's user data JSON.
   */
  def apply(user: JValue) = new ExtendedUser(
    basicInfo = User(user),
    relationship = Relationship(user.get("relationship")),
    recruited = user.get("recruited"),
    about = user.getOption("about"),
    plurksCount = user.getOption("plurks_count")
  )

}
