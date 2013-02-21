package org.bone.soplurk.api

import org.bone.soplurk.api.PlurkAPI._
import org.bone.soplurk.oauth.PlurkOAuth
import org.bone.soplurk.oauth.MockOAuth
import org.bone.soplurk.model._
import org.bone.soplurk.constant.AlertType._

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.PrivateMethodTester 

import org.scribe.model.Verb

import net.liftweb.json.JsonAST._
import net.liftweb.json.JsonParser

import scala.util.{Try, Success, Failure}
import java.util.Date

object PlurkTopAPIMock extends PlurkOAuth(null) with MockOAuth {

  val collectionJSON = JsonParser.parse("""[
    ["Taiwan", "cn,tr_ch,en", "台灣"], 
    ["English", "en", "English"], 
    ["Philippines", "en,en_fo,ta_fp", "Pilipinas"]
  ]
  """);

  val topicJSON = JsonParser.parse("""[
    [1, "News", "News"], 
    [2, "Entertainment", "Entertainment"], 
    [0, "Interesting", "Interesting"], 
    [5, "Technology", "Technology"]
  ]""");


  override def sendRequest(url: String, method: Verb, 
                           params: (String, String)*): Try[JValue] = {

    def hasLang(language: String) = params.contains("lang" -> language)

    (url, method) match {

      case ("/APP/PlurkTop/getCollections", Verb.GET) => Success(collectionJSON)
      case ("/APP/PlurkTop/getTopics", Verb.GET) if hasLang("tr_ch") => Success(topicJSON)

      case _ => Failure(throw new Exception("Not implemented"))
    }

  }

}

class PlurkTopSpec extends FunSpec with ShouldMatchers {

  describe("A PlurkAPI with PlurkTop trait") {

    val plurkAPI = PlurkAPI.withMock(PlurkTopAPIMock)

    it ("get collections by /APP/PlurkTop/getCollections correctly") {
      val collections = plurkAPI.PlurkTop.getCollections.get
      collections should be === List(
        Collection("Taiwan", List("cn", "tr_ch", "en"), "台灣"),
        Collection("English", List("en"), "English"), 
        Collection("Philippines", List("en", "en_fo", "ta_fp"), "Pilipinas")
      )
    }

    it ("get topics by /APP/PlurkTop/getTopics correctly") {

      val topics = plurkAPI.PlurkTop.getTopics("tr_ch").get

      topics should be === List(
        Topic(1, "News", "News"), 
        Topic(2, "Entertainment", "Entertainment"), 
        Topic(0, "Interesting", "Interesting"), 
        Topic(5, "Technology", "Technology")
      )

    }

    it ("get plurks by /APP/PlurkTop/getPlurks correctly") {
      pending
    }

  }
}

