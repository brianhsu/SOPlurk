package org.bone.soplurk.oauth

import org.bone.soplurk.exceptions.RequestException
import org.bone.soplurk.model.MyJValueImplicits

import org.scribe.oauth.OAuthService
import org.scribe.model.Verb
import org.scribe.model.Token
import org.scribe.model.OAuthRequest
import org.scribe.model.Parameter

import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.methods.PostMethod
import org.apache.commons.httpclient.methods.multipart.FilePart
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity
import org.apache.commons.httpclient.methods.multipart.Part

import net.liftweb.json.JsonAST._
import net.liftweb.json.JsonParser

import scala.io.Source
import scala.collection.JavaConverters._
import scala.util.{Try, Success, Failure}

import java.io.File

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

    val request = buildRequest(url, method, params: _*)

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
    
    def stripOutCometPrefix = responseBody.startsWith("CometChannel.scriptCallback") match {
      case false => responseBody
      case true  => 
        val regex = """(CometChannel.scriptCallback\()(.*)(\).*)""".r
        regex.findFirstMatchIn(responseBody).map(_.group(2).trim).getOrElse(responseBody)
    }

    val isSuccess = code >= 200 && code < 400

    isSuccess match {
      case true  => parseResponseToJSON(code, stripOutCometPrefix)
      case false => Failure(
        JsonParser.parseOpt(responseBody).
                   map(x => new RequestException(code, x.get("error_text"))).
                   getOrElse(new RequestException(code, responseBody))
      )
    }
  }

  private def parseResponseToJSON(code: Int, responseBody: String) = Try {
    
    val response = JsonParser.parseOpt(responseBody)

    val result = response match {
      case None       => throw new RequestException(code, responseBody)
      case Some(json) =>

        val errorText = (json \\ "error_text")

        errorText match {
          case JString(msg) => throw new RequestException(code, "error_text:" + msg)
          case _ => json
        }

    }

    result
  }

  /**
   *  Create OAuthReqeust object and attatch params to it.
   *
   *  @param    url         Plurk's API URL.
   *  @param    method      HTTP method type.
   *  @param    params      Parameters to send.
   */
  private def buildRequest(url: String, 
                           method: Verb, 
                           params: (String, String)*): OAuthRequest = {

    val fullURL = if(url.startsWith("http")) url else plurkAPIPrefix + url
    val request = new OAuthRequest(method, fullURL)

    if (method == Verb.POST) {
      params.foreach { case(key, value) => request.addBodyParameter(key, value) }
    } else if (method == Verb.GET) {
      params.foreach { case(key, value) => request.addQuerystringParameter(key, value) }
    }

    accessToken.foreach {
      service.signRequest(_, request)
    }

    request
  }

  /**
   *  Upload file to Plurk
   *
   *  @param  url               The Plurk API request URL.
   *  @param  parameterName     The parameter name of API call.
   *  @param  file              The file you want to upload.
   */
  private def uploadFile(url: String, 
                         parameterName: String, 
                         file: File): Try[JValue] = Try {

    if (!file.isFile || !file.exists) {
      throw new RequestException(0, "File not found:" + file)
    }

    val request = buildRequest(url, Verb.POST)
    val oauthParams = request.getOauthParameters.asScala
    val queryString = oauthParams.map { case(name, value) => 
      new Parameter(name, value).asUrlEncodedPair
    }

    val httpClient = new HttpClient
    val fullURL = request.getCompleteUrl + queryString.mkString("?", "&", "")
    val post = new PostMethod(fullURL)

    try {

      val filePart = new FilePart(parameterName, file.getName(), file, "binary/octet-stream", "UTF-8")
      val parts = Array[Part](filePart)

      parts.foreach(_.asInstanceOf[FilePart].setTransferEncoding("binary"))
      post.setRequestEntity(new MultipartRequestEntity(parts, post.getParams()))

      val code = httpClient.executeMethod(post)
      val responseBody = Source.fromInputStream(post.getResponseBodyAsStream).mkString
      
      parseResponse(code, responseBody).get

    } finally {
      post.releaseConnection();
    }
  }

}

