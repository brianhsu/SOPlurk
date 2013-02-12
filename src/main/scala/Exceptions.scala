package org.bone.splurk2.exceptions

case class NoSuchQualifierException(qualifier: String) extends Exception(s"No such qualifier:  $qualifier")

