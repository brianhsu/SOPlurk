package org.bone.splurk2.exceptions

class NoSuchReadStatusException(val code: Byte) extends Exception(s"$code")
class NoSuchCommentSettingException(val code: Byte) extends Exception(s"$code")
class NoSuchBirthdayPrivacyException(val code: Byte) extends Exception(s"$code")
class NoSuchGenderException(val code: Byte) extends Exception(s"$code")
class NoSuchRelationshipException(val word: String) extends Exception(s"$word")
