package org.bone.soplurk.api

import org.bone.soplurk.model._
import org.bone.soplurk.api.PlurkAPI._
import org.bone.soplurk.api.TimelineParser._

import org.scribe.model.Verb

import scala.util.Try
import java.util.Date

trait Realtime {

  this: PlurkAPI =>

  import MyJValueImplicits._

  /**
   *  API of OAuth utils under /APP/
   */
  object Realtime {

    /**
     *  Get realtime channel of current user.
     *
     *  @return     Success[UserChannel] if get channel correctly. 
     */
    def getUserChannel: Try[UserChannel] = {
      
      val response = plurkOAuth.sendRequest("/APP/Realtime/getUserChannel", Verb.GET)

      response.map { jsonData =>

        val channelName = jsonData.get[String]("channel_name")
        val channelURL = jsonData.get[String]("comet_server")
        val regex = "(.*)&offset=(.*)".r
        val Some((cometServer, offset)) = {
          regex.findFirstMatchIn(channelURL).map(x => (x.group(1), x.group(2)))
        }

        UserChannel(cometServer, channelName, offset.toInt)
      }

    }

  }

}

