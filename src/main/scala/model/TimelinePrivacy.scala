package org.bone.splurk2.model

sealed abstract class TimelinePrivacy(val word: String)

object TimlinePrivacy {
  case object World extends TimelinePrivacy("world")
  case object OnlyFriends extends TimelinePrivacy("only_firends")
}

