package org.bone.soplurk.api

import org.bone.soplurk.model._
import org.bone.soplurk.api.PlurkAPI._
import org.bone.soplurk.api.TimelineParser._

import org.scribe.model.Verb

import scala.util.Try
import java.util.Date

trait Blocks {

  this: PlurkAPI =>

  import MyJValueImplicits._

  /**
   *  API of /APP/Blocks/
   */
  object Blocks {

    /**
     *  Get block's list of logged in user
     *
     *  The returned value will be a tuple of `(Int, List[User])`, where the first
     *  element is total user's that has been blocked. Second element is the current
     *  page of blocked user list.
     *
     *  @param    offset  What page should be shown.
     *  @return           Success((totalBlockUsers, users)) if everything is fine.
     */
    def get(offset: Int = 0): Try[(Int, List[User])] = {
      
      val params = List("offset" -> offset.toString).filterNot(_._2 == "0")
      val response = plurkOAuth.sendRequest("/APP/Blocks/get", Verb.GET, params: _*)

      response.map { jsonData =>
        val total = jsonData.get[Int]("total")
        val users = (jsonData \ "users").children.map(userJS => User(userJS))

        (total, users)
      }

    }

  }

}

