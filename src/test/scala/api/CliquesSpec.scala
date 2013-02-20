package org.bone.soplurk.api

import org.bone.soplurk.api.PlurkAPI._
import org.bone.soplurk.oauth.PlurkOAuth
import org.bone.soplurk.oauth.MockOAuth
import org.bone.soplurk.model._

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.PrivateMethodTester 

import org.scribe.model.Verb

import net.liftweb.json.JsonAST._
import net.liftweb.json.JsonParser

import scala.util.{Try, Success, Failure}
import java.util.Date

object CliquesAPIMock extends PlurkOAuth(null) with MockOAuth {

  val getCliquesResponse = JsonParser.parse("""[
    "C0", "C1", "C2"
  ]""")

  val getCliqueResponse = JsonParser.parse("""[
    {
      "verified_account": false,
      "default_lang": "tr_ch",
      "display_name": "\u56e7\u56e7\u4eba\u751f.mp4",
      "dateformat": 0,
      "nick_name": "ppew",
      "has_profile_image": 1,
      "location": "Taipei, Taiwan",
      "bday_privacy": 2,
      "date_of_birth": "Fri, 20 Jan 1984 00:01:00 GMT",
      "karma": 124.25,
      "full_name": "\u5289\u8000\u7fa4",
      "gender": 1,
      "name_color": "2264D6",
      "timezone": "Asia\/Taipei",
      "id": 4147596,
      "avatar": 2
    },
    {
      "verified_account": false,
      "default_lang": "tr_ch",
      "display_name": "\u5403\u98fd\u6c92\u4e8b\u5e79\u7684 ERIOL",
      "dateformat": 0,
      "nick_name": "xeriol",
      "has_profile_image": 1,
      "location": "Taipei, Taiwan",
      "bday_privacy": 2,
      "date_of_birth": "Thu, 02 Aug 1984 00:01:00 GMT",
      "karma": 127.03,
      "full_name": "\u99ac\u5c0f\u6fa4",
      "gender": 1,
      "name_color": null,
      "timezone": null,
      "id": 4055276,
      "avatar": 16
    }
  ]""")

  val successJSON = JsonParser.parse("""{"success_text": "ok"}""")

  override def sendRequest(url: String, method: Verb, 
                           params: (String, String)*): Try[JValue] = {


    def hasCliqueName(name: String) = params.contains("clique_name" -> name)
    def hasNewName(name: String) = params.contains("new_name" -> name)
    def hasUserID(id: Long) = params.contains("user_id" -> id.toString)

    (url, method) match {

      case ("/APP/Cliques/getCliques", Verb.GET) => 
        Success(getCliquesResponse)

      case ("/APP/Cliques/getClique", Verb.GET) if hasCliqueName("C0") => 
        Success(getCliqueResponse)

      case ("/APP/Cliques/createClique", Verb.POST) if hasCliqueName("Create") =>
        Success(successJSON)

      case ("/APP/Cliques/renameClique", Verb.POST) if hasCliqueName("Rename") &&
                                                       hasNewName("newName") =>
        Success(successJSON)

      case ("/APP/Cliques/deleteClique", Verb.POST) if hasCliqueName("Delete") =>
        Success(successJSON)

      case ("/APP/Cliques/add", Verb.POST) if hasCliqueName("add") && 
                                              hasUserID(1234L) =>
        Success(successJSON)

      case ("/APP/Cliques/remove", Verb.POST) if hasCliqueName("remove") && 
                                                 hasUserID(1234L) =>
        Success(successJSON)

      case _ => 
        Failure(throw new Exception("Not implemented"))
    }

  }

}

class CliquesSpec extends FunSpec with ShouldMatchers {

  describe("A PlurkAPI with Cliques trait") {

    val plurkAPI = PlurkAPI.withMock(CliquesAPIMock)

    it ("get cliques list by /APP/Cliques/getCliques correctly") {
      val cliques = plurkAPI.Cliques.getCliques.get
      cliques should be === List("C0", "C1", "C2")
    }

    it ("get user list of clique by /APP/Cliques/getClique correctly") {
      val users = plurkAPI.Cliques.getClique("C0").get
      users.map(_.id) should be === List(4147596L, 4055276L)
    }

    it ("create clique by /APP/Cliques/createClique correctly") {
      val isOK = plurkAPI.Cliques.createClique("Create").get
      isOK should be === true
    }

    it ("rename clique by /APP/Cliques/renameClique correctly") {
      val isOK = plurkAPI.Cliques.renameClique("Rename", "newName").get
      isOK should be === true
    }

    it ("delete clique by /APP/Cliques/deleteClique correctly") {
      val isOK = plurkAPI.Cliques.deleteClique("Delete").get
      isOK should be === true
    }

    it ("add user to clique by /APP/Cliques/add correctly") {
      pending
    }

    it ("remove user from clique by /APP/Cliques/remove correctly") {
      pending
    }

  }
}

