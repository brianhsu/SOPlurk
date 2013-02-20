package org.bone.soplurk.model

/**
 *  This object corresponding to Plurk's qualifier.
 *
 *  Each qualifier in Plurk is represtend as a case object which
 *  extends abstract class Qualifier, and has english qualifier as
 *  it's name.
 *
 *  @param  name    English name of this qualifier
 */
sealed abstract class Qualifier(val name: String)

/** *
 *  We could parse the qualifier string returned from Plurk's
 *  API to our case object using Qualifier.apply().
 */
object Qualifier {

  /**
   *  Convert english qualifier string to case object that represented the qualifier.
   *  
   *  @param    name    qualifier field in Plurk JSON.
   *  @return           The corresponding case object.
   */
  def apply(name: String) = name match {
    case ":"         => ::
    case "loves"     => Loves
    case "likes"     => Likes
    case "shares"    => Shares
    case "gives"     => Gives
    case "hates"     => Hates
    case "wants"     => Wants
    case "has"       => Has
    case "will"      => Will
    case "asks"      => Asks
    case "wishes"    => Wishes
    case "was"       => Was
    case "feels"     => Feels
    case "thinks"    => Thinks
    case "says"      => Says
    case "is"        => Is
    case "hopes"     => Hopes
    case "needs"     => Needs
    case "wonders"   => Wonders
    case "freestyle" => FreeStyle
    case "whispers"  => Whispers
    case _ => new Qualifier(name) {}
  }

  case object :: extends Qualifier(":")
  case object Loves extends Qualifier("loves")
  case object Likes extends Qualifier("likes")
  case object Shares extends Qualifier("shares")
  case object Gives extends Qualifier("gives")
  case object Hates extends Qualifier("hates")
  case object Wants extends Qualifier("wants")
  case object Has extends Qualifier("has")
  case object Will extends Qualifier("will")
  case object Asks extends Qualifier("asks")
  case object Wishes extends Qualifier("wishes")
  case object Was extends Qualifier("was")
  case object Feels extends Qualifier("feels")
  case object Thinks extends Qualifier("thinks")
  case object Says extends Qualifier("says")
  case object Is extends Qualifier("is")
  case object FreeStyle extends Qualifier("freestyle")
  case object Hopes extends Qualifier("hopes")
  case object Needs extends Qualifier("needs")
  case object Wonders extends Qualifier("wonders")
  case object Whispers extends Qualifier("whispers")
}
