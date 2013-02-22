package org.bone.soplurk.api

import org.bone.soplurk.model._
import org.bone.soplurk.api.PlurkAPI._
import org.bone.soplurk.api.TimelineParser._

import org.scribe.model.Verb

import scala.util.Try
import java.util.Date

trait OAuthUtils {

  this: PlurkAPI =>

  import MyJValueImplicits._

  /**
   *  API of OAuth utils under /APP/
   */
  object OAuthUtils {


    private def toDate(dateString: String): Date = {
      import java.text.SimpleDateFormat
      import java.util.Locale

      val dateFormatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US)
      dateFormatter.parse(dateString)
    }

    /**
     *  Check if current access token is valid and return information for this token.
     *
     *  @return   Success[TokenInfo] if current token is valid.
     */
    def checkToken: Try[TokenInfo] = {
      val response = plurkOAuth.sendRequest("/APP/checkToken", Verb.GET)

      response.map { jsonData =>
        TokenInfo(
          appID = jsonData.get("app_id"),
          userID = jsonData.get("user_id"),
          issued = toDate(jsonData.get[String]("issued")),
          deviceID = jsonData.get("deviceid")
        )
      }
    }

    /**
     *  Expire current access token 
     *
     *  @return   Success[TokenInfo] if expire token correctly.
     */
    def expireToken: Try[TokenInfo] = {
      val response = plurkOAuth.sendRequest("/APP/expireToken", Verb.POST)

      response.map { jsonData =>
        TokenInfo(
          appID = jsonData.get("app_id"),
          userID = jsonData.get("user_id"),
          issued = toDate(jsonData.get[String]("issued")),
          deviceID = jsonData.get("deviceid")
        )
      }
    }

    /**
     *  Get current time of Plurk servers.
     *
     *  @return   Success[TimeInfo] if get data successfully.
     */
    def checkTime: Try[TimeInfo] = {
      
      val response = plurkOAuth.sendRequest("/APP/checkTime", Verb.GET)

      response.map { jsonData =>
        
        TimeInfo(
          appID = jsonData.get("app_id"),
          userID = jsonData.getOption("user_id"),
          timestamp = jsonData.get("timestamp"),
          now = toDate(jsonData.get("now"))
        )
      }

    }

    /**
     *  Test for argument passing
     *
     *  This method will return the `data` you passed in.
     *
     *  @return   Success[(length, data)] if success.
     *
     */
    def echo(data: String): Try[(Int, String)] = {
      
      val response = plurkOAuth.sendRequest("/APP/echo", Verb.POST, "data" -> data)

      response.map { jsonData =>
        (jsonData.get[Int]("length"), jsonData.get("data"))
      }

    }



  }

}
