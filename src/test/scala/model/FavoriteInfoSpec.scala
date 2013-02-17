package org.bone.soplurk.model

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

import net.liftweb.json._

import org.bone.soplurk.exceptions._
import org.bone.soplurk.model._
import org.bone.soplurk.model.PlurkType._

class FavoriteInfoSpec extends FunSpec with ShouldMatchers {

  import FavoriteInfoSpec._

  describe("A FavoriteInfo") {

    it ("should able to parse plurks without favorers") {
      val correctInfo = FavoriteInfo(
        isFavorite = false,
        favoriteCount = 0,
        favorers = None
      )

      FavoriteInfo(withoutFavorers) should be === correctInfo
    }

    it ("should able to parse plurks without favorers in minimal JSON") {
      val correctInfo = FavoriteInfo(
        isFavorite = false,
        favoriteCount = 0,
        favorers = None
      )

      FavoriteInfo(withoutFavorersMinimal) should be === correctInfo
    }

    it ("should able to parse plurks with favorers") {
      val correctInfo = FavoriteInfo(
        isFavorite = true,
        favoriteCount = 99,
        favorers = Some(List(1367985L, 3371337L, 3496438L, 3588660L))
      )

      FavoriteInfo(withFavorers) should be === correctInfo
    }

    it ("should able to parse plurks with favorers in minimal JSON") {
      val correctInfo = FavoriteInfo(
        isFavorite = false,
        favoriteCount = 3,
        favorers = None
      )

      FavoriteInfo(withFavorersMinimal) should be === correctInfo
    }

  }
}

object FavoriteInfoSpec {
  val withoutFavorers = JsonParser.parse("""{
    "replurkers_count": 0,
    "replurkable": true,
    "favorite_count": 0,
    "is_unread": 0,
    "content": "Without Favorers",
    "user_id": 1367985,
    "plurk_type": 0,
    "qualifier_translated": "喜歡",
    "replurked": false,
    "favorers": [],
    "replurker_id": null,
    "owner_id": 4426947,
    "responses_seen": 0,
    "qualifier": "likes",
    "plurk_id": 1097417709,
    "response_count": 17,
    "limited_to": null,
    "no_comments": 0,
    "posted": "Wed, 13 Feb 2013 01:40:17 GMT",
    "lang": "tr_ch",
    "content_raw": "Without Favorers raw",
    "replurkers": [],
    "favorite": false
  }""")

  val withoutFavorersMinimal = JsonParser.parse("""{
    "replurkers_count": 0,
    "replurkable": true,
    "favorite_count": 0,
    "is_unread": 0,
    "content": "Without Favorers",
    "user_id": 1367985,
    "plurk_type": 0,
    "qualifier_translated": "喜歡",
    "replurked": false,
    "replurker_id": null,
    "owner_id": 4426947,
    "qualifier": "likes",
    "plurk_id": 1097417709,
    "response_count": 17,
    "limited_to": null,
    "no_comments": 0,
    "posted": "Wed, 13 Feb 2013 01:40:17 GMT",
    "lang": "tr_ch",
    "favorite": false
  }""")

  val withFavorers = JsonParser.parse(""" {
    "replurkers_count": 63,
    "replurkable": true,
    "favorite_count": 99,
    "is_unread": 1,
    "content": "Plurk with favorers",
    "user_id": 1367985,
    "plurk_type": 0,
    "qualifier_translated": "說",
    "replurked": false,
    "favorers": [
        1367985,
        3371337,
        3496438,
        3588660
    ],
    "replurker_id": null,
    "owner_id": 4047193,
    "responses_seen": 0,
    "qualifier": "says",
    "plurk_id": 1097494570,
    "response_count": 54,
    "limited_to": null,
    "no_comments": 0,
    "posted": "Wed, 13 Feb 2013 08:30:04 GMT",
    "lang": "tr_ch",
    "content_raw": "Plurk with favorers raw",
    "replurkers": [
        3371337,
        3496438,
    ],
    "favorite": true
  }""")

  val withFavorersMinimal = JsonParser.parse(""" {
    "replurkers_count": 3,
    "replurkable": true,
    "favorite_count": 3,
    "is_unread": 0,
    "content": "With favorers plurk minimal",
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


