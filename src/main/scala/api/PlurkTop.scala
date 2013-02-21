package org.bone.soplurk.api

import org.bone.soplurk.model._
import org.bone.soplurk.api.PlurkAPI._
import org.bone.soplurk.api.TimelineParser._

import org.scribe.model.Verb

import net.liftweb.json.JsonAST._
import scala.util.Try

trait PlurkTop {

  this: PlurkAPI =>

  import MyJValueImplicits._

  /**
   *  API of /APP/PlurkTop/
   */
  object PlurkTop {
    
    /**
     *  Get collections of PlurkTop.
     *
     *  @return   Success[List[Collection]] if get data from Plurk successfully.
     */
    def getCollections: Try[List[Collection]] = {
      val response = plurkOAuth.sendRequest("/APP/PlurkTop/getCollections", Verb.GET)

      response.map { jsonData => 
        jsonData.children.map(c => Collection(c)) 
      }
    }

  }

}

