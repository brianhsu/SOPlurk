package org.bone.splurk2.model

import org.bone.splurk2.exceptions._
import org.bone.splurk2.model._
import org.bone.splurk2.model.PlurkType._

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

import java.util.Date
import net.liftweb.json._

class PlurkSpec extends FunSpec with ShouldMatchers {

  import PlurkSpec._

  describe("A Plurk") {

    it ("should able to parse plurk's JSON data") {
      val favoriteInfo = FavoriteInfo(false, 43, Some(3499367L :: 3569457L :: 3673035L :: Nil))
      val replurkInfo = ReplurkInfo(true, false, 1, None, Some(6454321L :: Nil))
      val correctPlurk = Plurk(
        plurkID = 1097524102L, ownerID = 4047193L, userID = 1367985L,
        qualifier = Qualifier.Says, 
        content = "NormalPlurkPost",
        plurkType = PlurkType.Public, 
        readStatus = ReadStatus.Read,
        whoIsCommentable = CommentSetting.Everyone,
        posted = new Date(1360752727000L), // 2013-02-13T10:52:07 GMT
        language = "tr_ch",
        responseCount = 21,
        replurkInfo = replurkInfo,
        favoriteInfo = favoriteInfo,
        qualifierTranslated = Some("說"),
        limitedTo = None,
        responsesSeen = Some(0),
        contentRaw = Some("NormalPlurkPostRaw")
      )

      Plurk(normalPlurk) should be === correctPlurk
      Plurk(normalPlurk).plurkURL should be === "http://www.plurk.com/p/i5fr0m"
    }

    it ("should able to parse plurks's miminal JSON data") {
      val favoriteInfo = FavoriteInfo(false, 43, None)
      val replurkInfo = ReplurkInfo(true, false, 1, None, None)
      val correctPlurk = Plurk(
        plurkID = 1097524102L, ownerID = 4047193L, userID = 1367985L,
        qualifier = Qualifier.Says, 
        content = "NormalPlurkPostMinimal",
        plurkType = PlurkType.Public, 
        readStatus = ReadStatus.Read,
        whoIsCommentable = CommentSetting.Everyone,
        posted = new Date(1360752727000L), // 2013-02-13T10:52:07 GMT
        language = "tr_ch",
        responseCount = 21,
        replurkInfo = replurkInfo,
        favoriteInfo = favoriteInfo,
        qualifierTranslated = Some("說"),
        limitedTo = None,
        responsesSeen = None,
        contentRaw = None
      )

      Plurk(normalPlurkMinimal) should be === correctPlurk
      Plurk(normalPlurkMinimal).plurkURL should be === "http://www.plurk.com/p/i5fr0m"
    }

    it ("should able to parse whisper plurk's JSON data") {
      val favoriteInfo = FavoriteInfo(false, 0, None)
      val replurkInfo = ReplurkInfo(true, false, 0, None, None)
      val correctPlurk = new Plurk(
        plurkID = 1097490754L, ownerID = 99999L, userID = 99999L,
        qualifier = Qualifier.Whispers,
        content = "偷偷說的 API 測試……",
        plurkType = PlurkType.AnonymousResponded,
        readStatus = ReadStatus.Read,
        whoIsCommentable = CommentSetting.Everyone,
        posted = new Date(1360743094000L), // 2013-02-13T08:11:34 GMT
        language = "tr_ch",
        responseCount = 3,
        replurkInfo = replurkInfo,
        favoriteInfo = favoriteInfo,
        qualifierTranslated = Some("偷偷說"),
        limitedTo = None,
        responsesSeen = Some(0),
        contentRaw = Some("偷偷說的 API 測試……")
      )

      Plurk(whisperPlurk) should be === correctPlurk
      Plurk(whisperPlurk).plurkURL should be === "http://www.plurk.com/p/i5f1aa"

    }

    it ("should able to parse whisper plurk's minimal JSON data") {
      val favoriteInfo = FavoriteInfo(false, 0, None)
      val replurkInfo = ReplurkInfo(true, false, 0, None, None)

      val correctPlurk = new Plurk(
        plurkID = 1097490754L, ownerID = 99999L, userID = 99999L,
        qualifier = Qualifier.Whispers,
        content = "偷偷說的 API 測試……",
        plurkType = PlurkType.AnonymousResponded,
        readStatus = ReadStatus.Read,
        whoIsCommentable = CommentSetting.Everyone,
        posted = new Date(1360743094000L), // 2013-02-13T08:11:34 GMT
        language = "tr_ch",
        responseCount = 3,
        replurkInfo = replurkInfo,
        favoriteInfo = favoriteInfo,
        qualifierTranslated = Some("偷偷說"),
        limitedTo = None,
        responsesSeen = None,
        contentRaw = None
      )

      Plurk(whisperPlurkMinimal) should be === correctPlurk
      Plurk(whisperPlurkMinimal).plurkURL should be === "http://www.plurk.com/p/i5f1aa"
    }

  }
}

object PlurkSpec {
  val normalPlurk = JsonParser.parse("""{
    "replurkers_count": 1,
    "replurkable": true,
    "favorite_count": 43,
    "is_unread": 0,
    "content": "NormalPlurkPost",
    "user_id": 1367985,
    "plurk_type": 0,
    "qualifier_translated": "說",
    "replurked": false,
    "favorers": [
        3499367, 
        3569457, 
        3673035,
    ],
    "replurker_id": null,
    "owner_id": 4047193,
    "responses_seen": 0,
    "qualifier": "says",
    "plurk_id": 1097524102,
    "response_count": 21,
    "limited_to": null,
    "no_comments": 0,
    "posted": "Wed, 13 Feb 2013 10:52:07 GMT",
    "lang": "tr_ch",
    "content_raw": "NormalPlurkPostRaw",
    "replurkers": [
        6454321
    ],
    "favorite": false
  }""")

  val normalPlurkMinimal = JsonParser.parse("""{
    "replurkers_count": 1,
    "replurkable": true,
    "favorite_count": 43,
    "is_unread": 0,
    "content": "NormalPlurkPostMinimal",
    "user_id": 1367985,
    "plurk_type": 0,
    "qualifier_translated": "說",
    "replurked": false,
    "replurker_id": null,
    "owner_id": 4047193,
    "qualifier": "says",
    "plurk_id": 1097524102,
    "response_count": 21,
    "limited_to": null,
    "no_comments": 0,
    "posted": "Wed, 13 Feb 2013 10:52:07 GMT",
    "lang": "tr_ch",
    "favorite": false
  }""")

  val whisperPlurk = JsonParser.parse("""{
    "responses_seen": 0,
    "qualifier": "whispers",
    "replurkers": [],
    "plurk_id": 1097490754,
    "response_count": 3,
    "replurkers_count": 0,
    "my_anonymous": true,
    "replurkable": true,
    "limited_to": null,
    "no_comments": 0,
    "favorite_count": 0,
    "is_unread": 0,
    "lang": "tr_ch",
    "favorers": [],
    "content_raw": "偷偷說的 API 測試……",
    "user_id": 99999,
    "plurk_type": 6,
    "qualifier_translated": "偷偷說",
    "replurked": false,
    "favorite": false,
    "content": "偷偷說的 API 測試……",
    "replurker_id": null,
    "posted": "Wed, 13 Feb 2013 08:11:34 GMT",
    "owner_id": 99999
  }""")

  val whisperPlurkMinimal = JsonParser.parse("""{
    "qualifier": "whispers",
    "plurk_id": 1097490754,
    "response_count": 3,
    "replurkers_count": 0,
    "my_anonymous": true,
    "replurkable": true,
    "limited_to": null,
    "no_comments": 0,
    "favorite_count": 0,
    "is_unread": 0,
    "lang": "tr_ch",
    "user_id": 99999,
    "plurk_type": 6,
    "qualifier_translated": "偷偷說",
    "replurked": false,
    "favorite": false,
    "content": "偷偷說的 API 測試……",
    "replurker_id": null,
    "posted": "Wed, 13 Feb 2013 08:11:34 GMT",
    "owner_id": 99999
  }""")
}


