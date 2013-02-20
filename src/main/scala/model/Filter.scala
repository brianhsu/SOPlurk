package org.bone.soplurk.model

sealed abstract class Filter(val word: String)

object Filter {
  
  case object OnlyUser extends Filter("only_user")
  case object OnlyResponded extends Filter("only_responded")
  case object OnlyPrivate extends Filter("only_private")
  case object OnlyFavorite extends Filter("only_favorite")

}

