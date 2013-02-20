package org.bone.soplurk.api

import org.bone.soplurk.model._
import org.bone.soplurk.api.PlurkAPI._
import org.bone.soplurk.api.TimelineParser._

import org.scribe.model.Verb

import scala.util.Try
import java.util.Date

trait Responses {

  this: PlurkAPI =>

  import MyJValueImplicits._

  /**
   *  API of /APP/Responses/
   */
  object Responses {

    import TimelineParser._


  }

}

