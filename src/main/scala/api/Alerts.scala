package org.bone.soplurk.api

import org.bone.soplurk.model._
import org.bone.soplurk.api.PlurkAPI._
import org.bone.soplurk.api.TimelineParser._

import org.scribe.model.Verb

import scala.util.Try

trait Alerts {

  this: PlurkAPI =>

  import MyJValueImplicits._

  /**
   *  API of /APP/Alerts/
   */
  object Alerts {
    
    /**
     *  Get current user's active alerts list.
     *
     *  @return   Success[List[Alert]] if everything is fine.
     */
    def getActive: Try[List[Alert]] = {

      val response = plurkOAuth.sendRequest("/APP/Alerts/getActive", Verb.GET)

      response.map { jsonData => 
        jsonData.children.map(alertJS => Alert(alertJS)) 
      }

    }

  }

}

