package org.bone.splurk2.model

import net.liftweb.json.JsonAST._

object MyJValueImplicits
{
  implicit class MyJValue(jValue: JValue) {
    def get[T](name: String) = (jValue \ name).values.asInstanceOf[T]
    def getOption[T](name: String) = (jValue \ name).toOpt.filter(_ != JNull).map(_.values.asInstanceOf[T])
    def get[T]: T = jValue.values.asInstanceOf[T]
    def getOpt[T]: Option[T] = jValue.toOpt.filter(_ != JNull).map(_.get[T])
  }
}

