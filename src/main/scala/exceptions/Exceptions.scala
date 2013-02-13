package org.bone.splurk2.exceptions

case class NoSuchQualifierException(qualifier: String) extends Exception(s"No such qualifier:  $qualifier")
case class NoSuchReadStatusException(code: Byte) extends Exception(s"No such read status:  $code")
case class NoSuchPlurkTypeException(code: Byte) extends Exception(s"No such plurkType:  $code")

