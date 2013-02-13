package org.bone.splurk2.model

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

import net.liftweb.json._

import org.bone.splurk2.exceptions._
import org.bone.splurk2.model._
import org.bone.splurk2.model.PlurkType._

class ReplurkInfoSpec extends FunSpec with ShouldMatchers {

  import ReplurkInfoSpec._

  describe("A ReplurkInfo") {

    it ("should able to parse plurks without replurkers") {

      val correctInfo = ReplurkInfo(
        isReplurkable = true, isReplurked = false,
        replurkersCount = 0,
        replurkerID = None, replurkers = None
      )

      ReplurkInfo(withoutReplurkers) should be === correctInfo
    }

    it ("should able to parse plurks without replurkers in minimal JSON") {

      val correctInfo = ReplurkInfo(
        isReplurkable = true, isReplurked = false,
        replurkersCount = 0,
        replurkerID = None, replurkers = None
      )

      ReplurkInfo(withoutReplurkersMinimal) should be === correctInfo

    }

    it ("should able to parse plurks with replurkers") {

      val correctInfo = ReplurkInfo(
        isReplurkable = true, isReplurked = false,
        replurkersCount = 3,
        replurkerID = Some(3648151L), 
        replurkers = Some(List(3648151L, 6946060L, 7877054L))
      )

      ReplurkInfo(withReplurkers) should be === correctInfo
    }

    it ("should able to parse plurks with replurkers in minimal JSON") {
      val correctInfo = ReplurkInfo(
        isReplurkable = true, isReplurked = false,
        replurkersCount = 3,
        replurkerID = Some(3648151L), 
        replurkers = None
      )

      ReplurkInfo(withReplurkersMinimal) should be === correctInfo
    }

  }
}

object ReplurkInfoSpec {

  val withoutReplurkers = JsonParser.parse(""" {
    "replurkers_count": 0,
    "replurkable": true,
    "favorite_count": 0,
    "is_unread": 0,
    "content": "Plurk without replurkers",
    "user_id": 1367985,
    "plurk_type": 0,
    "qualifier_translated": "",
    "replurked": false,
    "favorers": [],
    "replurker_id": null,
    "owner_id": 3833577,
    "responses_seen": 0,
    "qualifier": ":",
    "plurk_id": 1097445664,
    "response_count": 0,
    "limited_to": null,
    "no_comments": 0,
    "posted": "Wed, 13 Feb 2013 04:21:42 GMT",
    "lang": "tr_ch",
    "content_raw": "Plurk without replurkers raw",
    "replurkers": [],
    "favorite": false
  }""")

  val withoutReplurkersMinimal = JsonParser.parse(""" {
    "replurkers_count": 0,
    "replurkable": true,
    "favorite_count": 0,
    "is_unread": 0,
    "content": "Plurk without replurkers",
    "user_id": 1367985,
    "plurk_type": 0,
    "qualifier_translated": "",
    "replurked": false,
    "replurker_id": null,
    "owner_id": 3833577,
    "qualifier": ":",
    "plurk_id": 1097445664,
    "response_count": 0,
    "limited_to": null,
    "no_comments": 0,
    "posted": "Wed, 13 Feb 2013 04:21:42 GMT",
    "lang": "tr_ch",
    "favorite": false
  }""")

  val withReplurkers = JsonParser.parse("""{
    "replurkers_count": 3,
    "replurkable": true,
    "favorite_count": 3,
    "is_unread": 0,
    "content": "With replurkers",
    "user_id": 1367985,
    "plurk_type": 0,
    "qualifier_translated": "分享",
    "replurked": false,
    "favorers": [
        3532061,
        3801968,
        9312901
    ],
    "replurker_id": 3648151,
    "owner_id": 5359161,
    "responses_seen": 0,
    "qualifier": "shares",
    "plurk_id": 1097422860,
    "response_count": 5,
    "limited_to": null,
    "no_comments": 0,
    "posted": "Wed, 13 Feb 2013 02:15:02 GMT",
    "lang": "tr_ch",
    "content_raw": "With replurkers raw",
    "replurkers": [
        3648151,
        6946060,
        7877054
    ],
    "favorite": false

  }""")

  val withReplurkersMinimal = JsonParser.parse("""{
    "replurkers_count": 3,
    "replurkable": true,
    "favorite_count": 3,
    "is_unread": 0,
    "content": "Plurk with replurkers",
    "user_id": 1367985,
    "plurk_type": 0,
    "qualifier_translated": "分享",
    "replurked": false,
    "replurker_id": 3648151,
    "owner_id": 5359161,
    "qualifier": "shares",
    "plurk_id": 1097422860,
    "response_count": 5,
    "limited_to": null,
    "no_comments": 0,
    "posted": "Wed, 13 Feb 2013 02:15:02 GMT",
    "lang": "tr_ch",
    "favorite": false
  }""")

}


