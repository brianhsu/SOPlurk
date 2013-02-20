package org.bone.soplurk.oauth

import org.bone.soplurk.exceptions.RequestException
import org.bone.soplurk.model.MyJValueImplicits

import org.scribe.oauth.OAuthService
import org.scribe.model.Verb
import org.scribe.model.Token
import org.scribe.model.OAuthRequest

import net.liftweb.json.JsonAST._
import net.liftweb.json.JsonParser

import scala.util.{Try, Success, Failure}

trait MockOAuth extends PlurkOAuth

class PlurkOAuth(val service: OAuthService)  {

  private val plurkAPIPrefix = "http://www.plurk.com"

  /**
   *  Default's to empty token, so we can use two-legged OAuth API.
   */
  private[soplurk] var accessToken: Option[Token] = Some(new Token("", ""))

  /**
   *  Send request to Plurk OAuth API
   *
   *  @param    url         Plurk's API URL.
   *  @param    method      HTTP method type.
   *  @param    params      Parameters to send.
   */
  def sendRequest(url: String, method: Verb, params: (String, String)*): Try[JValue] = {

    val request = buildRequest(plurkAPIPrefix + url, method, params: _*)

    accessToken.foreach {
      service.signRequest(_, request)
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
      case true  => parseResponseToJSON(code, responseBody)
      case false => Failure(
        JsonParser.parseOpt(responseBody).
                   map(x => new RequestException(code, x.get("error_text"))).
                   getOrElse(new RequestException(code, responseBody))
      )
    }
  }

  private def parseResponseToJSON(code: Int, responseBody: String) = Try {
    
    val response = JsonParser.parse(responseBody)
    val errorText = (response \\ "error_text")

    errorText match {
      case JString(msg) => throw new RequestException(code, "error_text:" + msg)
      case _ =>
    }

    response
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

