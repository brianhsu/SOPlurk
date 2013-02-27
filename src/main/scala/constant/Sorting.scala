package org.bone.soplurk.constant

import org.bone.soplurk.exceptions._

/**
 *  Represented sorting option in PlurkTop.
 *
 *  @param  word    The value we need pass to PlurkTop API.
 */
sealed abstract class Sorting(val word: String)

object Sorting
{
  case object Hot extends Sorting("hot")
  case object New extends Sorting("new")
}
