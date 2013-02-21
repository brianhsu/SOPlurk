package org.bone.soplurk.model

import net.liftweb.json.JsonAST._


sealed abstract class Icon(val name: String, val url: String) {
  def insertText: String = name
}

case class Emoticon(
  override val name: String, 
  override val url: String) extends Icon(name, url)

object Emoticon {

  import MyJValueImplicits._

  def apply(jsonData: JValue): Emoticon = {
    Emoticon(
      name = jsonData(0).values.toString,
      url  = jsonData(1).values.toString
    )
  }
}

case class CustomIcon(
  override val name: String, 
  override val url: String) extends Icon(name, url) {

  override def insertText: String = s"[$name]"
}

object CustomIcon {

  import MyJValueImplicits._

  def apply(jsonData: JValue): CustomIcon = {
    CustomIcon(
      name = jsonData(0).values.toString,
      url  = jsonData(1).values.toString
    )
  }
}

