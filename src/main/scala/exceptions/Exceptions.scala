package org.bone.splurk2.exceptions

case class NoSuchReadStatusException(code: Byte) extends Exception(s"No such read status:  $code")
case class NoSuchCommentSettingException(code: Byte) extends Exception(s"No such comment setting:  $code")
