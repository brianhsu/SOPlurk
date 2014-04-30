package org.bone.soplurk.oauth

import org.bone.soplurk.exceptions.RequestException
import org.bone.soplurk.model.MyJValueImplicits

import org.scribe.oauth.OAuthService
import org.scribe.model.Verb
import org.scribe.model.Token
import org.scribe.model.OAuthRequest
import org.scribe.model.Response
import org.scribe.model.Parameter

import net.liftweb.json.JsonAST._
import net.liftweb.json.JsonParser

import scala.io.Source
import scala.collection.JavaConverters._
import scala.util.{Try, Success, Failure}

import java.io._
import java.net._


private[soplurk] trait MockOAuth extends PlurkOAuth // For unit-test mock

/**
 *  Plurk OAuth Util
 *
 *  @param  service       Scribe OAuth object that connect to Plurk.
 *  @param  accessToken   Sign request using this access token.
 */
class PlurkOAuth(val service: OAuthService, 
                 private[soplurk] var accessToken: Option[Token] = None)  {

  private val defaultToken = new Token("", "")
  private val plurkAPIPrefix = "http://www.plurk.com"

  /**
   *  Send request to Plurk OAuth API
   *
   *  @param    url         Plurk's API URL.
   *  @param    method      HTTP method type.
   *  @param    params      Parameters to send.
   */
  def sendRequest(url: String, method: Verb, params: (String, String)*): Try[JValue] = {

    val request = buildRequest(url, method, params: _*)

    service.signRequest(accessToken.getOrElse(defaultToken), request)

    Try {
      val response = request.send
      parseResponse(response.getCode, response.getBody).get
    }
  }

  /**
   *  Upload file to Plurk
   *
   *  @param  url               The Plurk API request URL.
   *  @param  parameterName     The parameter name of API call.
   *  @param  file              The file you want to upload.
   */
  def uploadFile(url: String, parameterName: String, 
                 file: File): Try[JValue] = Try {

    if (!file.isFile || !file.exists) {
      throw new RequestException(0, "File not found:" + file)
    }

    val fileInputStream = new FileInputStream(file)
    val fileSize = file.length()
    uploadFile(url, parameterName, file.getName, fileInputStream, fileSize).get
  }

  def uploadFile(url: String, parameterName: String, filename: String, data: InputStream, filesize: Long): Try[JValue] = Try {

    val startTime = System.currentTimeMillis
    val request = buildRequest(url, Verb.POST)
    accessToken.foreach { token => service.signRequest(token, request) }

    val boundary = "===" + System.currentTimeMillis() + "==="
    val connection = new URL(request.getCompleteUrl).openConnection.asInstanceOf[HttpURLConnection]
    val payloadHeader = createPayloadHeader(boundary, parameterName, filename).getBytes
    val headers = request.getHeaders().asScala

    headers.foreach { case(key, value) => connection.setRequestProperty(key, value) }
    connection.setRequestProperty("Content-Type", s"multipart/form-data; boundary=${boundary}")
    connection.setDoOutput(true)
    connection.setFixedLengthStreamingMode((payloadHeader.length + filesize).toInt)

    val outputStream = connection.getOutputStream
    outputStream.write(payloadHeader)
    outputStream.flush()

    val bufferedInputStream = new BufferedInputStream(data)
    val buffer = new Array[Byte](1024 * 512)
    var byteCounter = bufferedInputStream.read(buffer)

    while (byteCounter > 0) {
      outputStream.write(buffer, 0, byteCounter)
      byteCounter = bufferedInputStream.read(buffer)
    }
    outputStream.flush()
    outputStream.close()

    val responseCode = connection.getResponseCode
    val inputStream = if (responseCode >= 200 && responseCode < 400) connection.getInputStream else connection.getErrorStream
    val responseContent = scala.io.Source.fromInputStream(inputStream).mkString
    inputStream.close()
    val duration = (System.currentTimeMillis - startTime) / 1000.0

    println("duration:" + duration)

    parseResponse(responseCode, responseContent).get
  }


  private def createPayloadHeader(boundary: String, parameterName: String, filename: String): String = {

    val buffer = new StringBuffer()
    buffer.append(s"--${boundary}").append("\r\n")

    buffer.append(raw"""Content-Disposition: form-data; name="${parameterName}"; """)
    buffer.append(raw"""filename="${filename}"""").append("\r\n")

    buffer.append("Content-Type: application/octet-stream").append("\r\n")
    buffer.append("Content-Transfer-Encoding: binary").append("\r\n")
    buffer.append("\r\n")
    buffer.toString
  }

  private def createPayload(boundary: String, parameterName: String, file: File): Array[Byte] = {

    val bos = new ByteArrayOutputStream()
    val writer = new PrintWriter(bos)

    writer.append(createPayloadHeader(boundary, parameterName, file.getName))
    writer.flush()
    
    val inputStream = new FileInputStream(file)
    val buffer = new Array[Byte](4096)
    var bytesRead = inputStream.read(buffer)
    while (bytesRead != -1) {
      bos.write(buffer, 0, bytesRead)
      bytesRead = inputStream.read(buffer)
    }

    bos.flush()
    inputStream.close()

    bos.toByteArray
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

    request
  }

  /**
   *  Create OAuthReqeust object and attatch params to it.
   *
   *  @param    url         Plurk's API URL.
   *  @param    method      HTTP method type.
   *  @param    params      Parameters to send.
   */
  private def buildRequest(url: String, 
                           payload: Array[Byte],
                           method: Verb, 
                           params: (String, String)*): OAuthRequest = {

    val fullURL = if(url.startsWith("http")) url else plurkAPIPrefix + url
    val request = new OAuthRequest(method, fullURL)

    if (method == Verb.POST) {
      params.foreach { case(key, value) => request.addBodyParameter(key, value) }
    } else if (method == Verb.GET) {
      params.foreach { case(key, value) => request.addQuerystringParameter(key, value) }
    }

    request
  }

}

