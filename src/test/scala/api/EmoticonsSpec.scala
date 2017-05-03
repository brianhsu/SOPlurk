package org.bone.soplurk.api

import org.bone.soplurk.api.PlurkAPI._
import org.bone.soplurk.oauth.PlurkOAuth
import org.bone.soplurk.oauth.MockOAuth
import org.bone.soplurk.model._
import org.bone.soplurk.constant.AlertType._

import org.scalatest.FunSpec
import org.scalatest.Matchers
import org.scalatest.PrivateMethodTester 

import org.scribe.model.Verb

import net.liftweb.json.JsonAST._
import net.liftweb.json.JsonParser

import scala.util.{Try, Success, Failure}
import java.util.Date

object EmoticonsAPIMock extends PlurkOAuth(null) with MockOAuth {

  val getResponse = JsonParser.parse("""{
    "custom": [
        ["O_O","http://emos.plurk.com/1.gif"],
        ["kininarimasu","http://emos.plurk.com/2.gif"],
    ],
    "recruited": {
        "10": [
            ["(bigeyes)", "http://statics.plurk.com/1.gif"],
            ["(funkydance)","http://statics.plurk.com/2.gif"],
            ["(funkydance)","http://statics.plurk.com/3.gif"]
        ]
    },
    "karma": {
        "0": [
            [":-))", "http://statics.plurk.com/4.gif"],
            [":-)", "http://statics.plurk.com/5.gif"]
        ],
        "25": [
            ["(:", "http://statics.plurk.com/6.gif"],
            ["(K)","http://statics.plurk.com/7.gif"],
            ["(angry)","http://statics.plurk.com/8.gif"],
            ["(annoyed)", "http://statics.plurk.com/9.gif"]
        ]
    }
  }""")

  val successJSON = JsonParser.parse("""{"success_text": "ok"}""")


  override def sendRequest(url: String, method: Verb, 
                           params: (String, String)*): Try[JValue] = {


    def hasURL(url: String) = params.contains("url" -> url)
    def hasKeyword(keyword: String) = params.contains("keyword" -> keyword)

    (url, method) match {

      case ("/APP/Emoticons/get", Verb.GET) => Success(getResponse)
      case ("/APP/Emoticons/delete", Verb.POST) if hasKeyword("delete") => Success(successJSON)
      case ("/APP/Emoticons/addFromURL", Verb.POST) if hasURL("http://aa.bb/add.gif") &&
                                                       hasKeyword("add") => 
        Success(successJSON)

      case _ => Failure(throw new Exception("Not implemented"))
    }

  }

}

class EmoticonsSpec extends FunSpec with Matchers {

  describe("A PlurkAPI with Emoticons trait") {

    val plurkAPI = PlurkAPI.withMock(EmoticonsAPIMock)

    it ("get icons list by /APP/Emoticons/get correctly") {
      val emoticons = plurkAPI.Emoticons.get.get

      emoticons.custom shouldBe List(
        CustomIcon("O_O","http://emos.plurk.com/1.gif"),
        CustomIcon("kininarimasu","http://emos.plurk.com/2.gif")
      )

      emoticons.recruited.size shouldBe 1
      emoticons.recruited(10).size shouldBe 3
      emoticons.karma.size shouldBe 2
      emoticons.karma(0).size shouldBe 2
      emoticons.karma(25).size shouldBe 4
    }

    it ("add custom icons by /APP/Emoticons/addFromURL correctly") {
      val isOK = plurkAPI.Emoticons.addFromURL("http://aa.bb/add.gif", "add").get
      isOK shouldBe true
    }

    it ("remove custom icons by /APP/Emoticons/delete correctly") {
      val isOK = plurkAPI.Emoticons.delete("delete").get
      isOK shouldBe true
    }



  }
}

