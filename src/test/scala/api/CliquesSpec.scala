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

  override def sendRequest(url: String, method: Verb, 
                           params: (String, String)*): Try[JValue] = {


    def hasCliqueName(name: String) = params.contains("clique_name" -> name)

    (url, method) match {

      case ("/APP/Cliques/getCliques", Verb.GET) => 
        Success(getCliquesResponse)

      case ("/APP/Cliques/getClique", Verb.GET) if hasCliqueName("C0") => 
        Success(getCliqueResponse)

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
      pending
    }

    it ("rename clique by /APP/Cliques/renameClique correctly") {
      pending
    }

    it ("delete clique by /APP/Cliques/deleteClique correctly") {
      pending
    }

    it ("add user to clique by /APP/Cliques/add correctly") {
      pending
    }

    it ("remove user from clique by /APP/Cliques/remove correctly") {
      pending
    }

  }
}

