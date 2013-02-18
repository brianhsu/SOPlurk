package org.bone.soplurk.api

import org.bone.soplurk.model._
import net.liftweb.json.JsonAST._

private[soplurk] object TimelineParser {
  import scala.language.implicitConversions

  implicit private def toJField(x: JValue) = x.asInstanceOf[JField]

  def parsePlurks(plurks: JValue) = plurks.children.map(Plurk.apply)
  def parseUsersMap(users: JValue) = users.children.map { user =>
    (user.name.toLong, User(user))
  }.toMap

}

