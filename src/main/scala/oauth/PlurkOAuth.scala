package org.bone.splurk2.oauth

import org.bone.splurk2.exceptions.RequestException
import org.bone.splurk2.model.MyJValueImplicits

import org.scribe.oauth.OAuthService
import org.scribe.model.Verb
import org.scribe.model.Token
import org.scribe.model.OAuthRequest

import net.liftweb.json.JsonAST._
import net.liftweb.json.JsonParser

import scala.util.{Try, Success, Failure}

class PlurkOAuth(val plurkService: OAuthService)  {

  var accessToken: Option[Token] = None

  /**
   *  Send request to Plurk OAuth API
   *
   *  @param    url         Plurk's API URL.
   *  @param    method      HTTP method type.
   *  @param    params      Parameters to send.
   */
  def sendRequest(url: String, method: Verb, params: (String, String)*): Try[JValue] = {
    val request = buildRequest(url, method, params: _*)

    accessToken.foreach {
      plurkService.signRequest(_, request)
    }

    Try {
      val response = request.send
      parseResponse(response.getCode, response.getBody).get
    }
  }

  /**
   *  Parse response returned by Plurk API to JSON object.
   *
   *  It will return a Failure when there is exception.
   *
   *  If connection is OK, but Plurk has responded HTTP error code,
   *  the Failure will contains RequestException.
   *
   *
   *  @param    code            HTTP return code
   *  @param    responseBody    The body of response.
   *  @return                   Success[JValue] if it's a success call.
   */
  private def parseResponse(code: Int, responseBody: String): Try[JValue] = {

    import MyJValueImplicits._
    
    val isSuccess = code >= 200 && code < 400

    isSuccess match {
      case true  => Success(JsonParser.parse(responseBody))
      case false => Failure(
        JsonParser.parseOpt(responseBody).
                   map(x => new RequestException(code, x.get("error_text"))).
                   getOrElse(new RequestException(code, responseBody))
      )
    }
  }

  /**
   *  Create OAuthReqeust object and attatch params to it.
   *
   *  @param    url         Plurk's API URL.
   *  @param    method      HTTP method type.
   *  @param    params      Parameters to send.
   */
  private def buildRequest(url: String, method: Verb, params: (String, String)*): OAuthRequest = {

    val request = new OAuthRequest(method, url)

    if (method == Verb.POST) {
      params.foreach { case(key, value) => request.addBodyParameter(key, value) }
    } else if (method == Verb.GET) {
      params.foreach { case(key, value) => request.addQuerystringParameter(key, value) }
    }

    request
  }


}

