package org.bone.soplurk.api

import org.bone.soplurk.model._

import org.scribe.model.Verb

import java.util.Date

import scala.util.Try

trait Polling {

  this: PlurkAPI =>

  import MyJValueImplicits._
  import java.text.SimpleDateFormat

  /**
   *  API of /APP/Polling/
   */
  object Polling {

    import TimelineParser._

  }

}

