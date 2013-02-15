package org.bone.splurk2.oauth

import org.bone.splurk2.exceptions.RequestException

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.PrivateMethodTester 

import org.scribe.model.OAuthRequest
import org.scribe.model.Parameter
import org.scribe.model.Verb

import scala.util.{Try, Success, Failure}
import net.liftweb.json.JsonAST._
import net.liftweb.json.JsonDSL._

class PlurkOAuthSpec extends FunSpec with ShouldMatchers with PrivateMethodTester {

  describe("A PlurkOAuth") {

    it ("should build GET request with param correct") {

      val plurkOAuth = new PlurkOAuth(null)
      val buildRequest = PrivateMethod[OAuthRequest]('buildRequest)

      val request = plurkOAuth invokePrivate buildRequest (
        "http://localhost/get", Verb.GET,
        List(
          "option1" -> "HelloWorld",
          "option2" -> "Foo",
          "option3" -> "Bar"
        )
      )

      request.getUrl should be === "http://localhost/get"
      request.getVerb should be === Verb.GET
      request.getBodyParams.size should be === 0

      val buildParams = request.getQueryStringParams
      buildParams.size should be === 3
      buildParams.contains(new Parameter("option1", "HelloWorld")) should be === true
      buildParams.contains(new Parameter("option2", "Foo")) should be === true
      buildParams.contains(new Parameter("option3", "Bar")) should be === true
    }

    it ("should build POST request with param correct") {

      val plurkOAuth = new PlurkOAuth(null)
      val buildRequest = PrivateMethod[OAuthRequest]('buildRequest)

      val request = plurkOAuth invokePrivate buildRequest(
        "http://localhost/post", Verb.POST,
        List(
          "option1" -> "HelloWorld",
          "option2" -> "Foo",
          "option3" -> "Bar",
          "option4" -> "FooBar"
        )
      )
      
      request.getUrl should be === "http://localhost/post"
      request.getVerb should be === Verb.POST
      request.getQueryStringParams.size should be === 0

      val buildParams = request.getBodyParams
      buildParams.size should be === 4
      buildParams.contains(new Parameter("option1", "HelloWorld")) should be === true
      buildParams.contains(new Parameter("option2", "Foo")) should be === true
      buildParams.contains(new Parameter("option3", "Bar")) should be === true
      buildParams.contains(new Parameter("option4", "FooBar")) should be === true
    }

    it ("should parse HTTP 200 with response to Success[JValue] object") {

      val mockJSON = """{
         "lang": "en", 
         "posted": "Fri, 05 Jun 2009 23:07:13 GMT", 
         "qualifier": "thinks", 
         "content": "test me out"
      }"""

      val plurkOAuth = new PlurkOAuth(null)
      val parseResponse = PrivateMethod[Try[JValue]]('parseResponse)
      val responseJSON = plurkOAuth invokePrivate parseResponse(200, mockJSON)

      responseJSON.isSuccess should be === true
      responseJSON.get should be === (
        ("lang" -> "en") ~
        ("posted" -> "Fri, 05 Jun 2009 23:07:13 GMT") ~
        ("qualifier" -> "thinks") ~
        ("content" -> "test me out")
      )
    }

    it ("should parse HTTP 400 with JSON response to Failure contains error message from JSON") {

      val mockJSON = """{
        "error_text": "Email invalid"
      }"""

      val plurkOAuth = new PlurkOAuth(null)
      val parseResponse = PrivateMethod[Try[JValue]]('parseResponse)
      val responseJSON = plurkOAuth invokePrivate parseResponse(400, mockJSON)

      responseJSON.isFailure should be === true

      val exception = intercept[RequestException] {
        responseJSON.get
      }

      exception.code should be === 400
      exception.message should be === "Email invalid"
    }

    it ("should parse HTTP 4xx with HTML response to Failure contains original contents") {

      val mockHTML = """<html><head><title>Not Found</title></head></html>"""

      val plurkOAuth = new PlurkOAuth(null)
      val parseResponse = PrivateMethod[Try[JValue]]('parseResponse)
      val responseJSON = plurkOAuth invokePrivate parseResponse(404, mockHTML)

      responseJSON.isFailure should be === true

      val exception = intercept[RequestException] {
        responseJSON.get
      }

      exception.code should be === 404
      exception.message should be === mockHTML
    }

  }
}

