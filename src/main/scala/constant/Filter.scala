package org.bone.soplurk.constant

sealed abstract class Filter(val word: String, val unreadWord: String)

object Filter {
  
  case object OnlyUser extends Filter("only_user", "my")
  case object OnlyResponded extends Filter("only_responded", "responded")
  case object OnlyPrivate extends Filter("only_private", "private")
  case object OnlyFavorite extends Filter("only_favorite", "favorite")

}

