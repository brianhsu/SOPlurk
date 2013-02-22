package org.bone.soplurk.model

import org.bone.soplurk.constant._
import org.bone.soplurk.exceptions._
import org.bone.soplurk.model._

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

import net.liftweb.json._

class TopPlurkSpec extends FunSpec with ShouldMatchers {

  import TopPlurkSpec._

  describe("A TopPlurk") {

    it ("should able to parse JSON return by PlurkTop") {
      val topPlurk = TopPlurk(topPlurkJSON)

      topPlurk.plurk.plurkID should be === 1100143868L
      topPlurk.voteUp should be === 2
      topPlurk.voteDown should be === 1
      topPlurk.score should be === 11373.3007812
      topPlurk.topicID should be === 6
    }

  }
}

object TopPlurkSpec {
  val topPlurkJSON = JsonParser.parse("""{
    "replurkers_count": 2,
    "replurkable": true,
    "favorite_count": 2,
    "is_unread": 0,
    "favorers": [
        7609704,
        9071198
    ],
    "vote_user": 0,
    "user_id": 8945584,
    "plurk_type": 0,
    "replurked": null,
    "poster_uid": 7609704,
    "content": "Content",
    "plurk_id": 1100143868,
    "score": 11373.3007812,
    "topic_id": 6,
    "owner_id": 8945584,
    "vote_up": 2,
    "responses_seen": 0,
    "qualifier": "shares",
    "replurker_id": null,
    "response_count": 2,
    "limited_to": null,
    "no_comments": 0,
    "posted": "Fri, 22 Feb 2013 00:27:00 GMT",
    "lang": "en",
    "vote_down": 1,
    "content_raw": "ContentRaw",
    "replurkers": [
        8756560,
        9071198
    ],
    "entry_posted": 1361492993
  }""")
}


