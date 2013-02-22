package org.bone.soplurk.model

import net.liftweb.json.JsonAST._

object MyJValueImplicits
{
  
  implicit val jsToList: JValue => List[String] = (x: JValue) => x match {
    case JArray(xs) => xs.toList.map(jsToString)
    case JString(s) => s.drop(1).dropRight(1).split("\\|\\|").toList
    case _ => throw new Exception("Not list:" + x)
  }

  implicit val jsToList2: JValue => List[Long] = (x: JValue) => x match {
    case JArray(xs) => xs.toList.map(jsToLong)
    case JString(s) => s.drop(1).dropRight(1).split("\\|\\|").map(_.toLong).toList
    case _ => throw new Exception("Not list" + x)
  }

  implicit val jsToInt: JValue => Int = (x: JValue) => x match {
    case JInt(num) => num.toInt
    case other => Integer.parseInt(other.toString)
  }

  implicit val jsToLong: JValue => Long = (x: JValue) => x match {
    case JInt(num) => num.toLong
    case other => java.lang.Long.parseLong(other.toString)
  }

  implicit val jsToDouble: JValue => Double = (x: JValue) => x match {
    case JInt(num) => num.toDouble
    case JDouble(num) => num
    case other => java.lang.Double.parseDouble(other.toString)
  }

  implicit val jsToBoolean: JValue => Boolean = (x: JValue) => x match {
    case JBool(value) => value
    case other => java.lang.Boolean.parseBoolean(other.toString)
  }

  implicit val jsToString: JValue => String = (x: JValue) => x match {
    case JString(x) => x.toString
    case value => value.toString
  }

  implicit class MyJValue(jValue: JValue) {

    def get[T](name: String)(implicit converter: JValue => T) = converter((jValue \ name))
    def getOption[T](name: String)(implicit converter: JValue => T) = {
      (jValue \ name).toOpt.filter(_ != JNull).map(x => converter(x))
    }
  }
}

