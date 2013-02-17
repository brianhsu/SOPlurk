package org.bone.soplurk.model

sealed abstract class TimelinePrivacy(val word: String)

object TimelinePrivacy {
  case object World extends TimelinePrivacy("world")
  case object OnlyFriends extends TimelinePrivacy("only_firends")

  def apply(word: String) = word match {
    case "world" => World
    case "OnlyFriends" => OnlyFriends
  }
}

