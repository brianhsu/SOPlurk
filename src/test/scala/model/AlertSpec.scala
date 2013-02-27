package org.bone.soplurk.model

import org.bone.soplurk.constant._
import org.bone.soplurk.exceptions._
import org.bone.soplurk.model._

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

import java.util.Date
import net.liftweb.json._

class AlertSpec extends FunSpec with ShouldMatchers {

  import AlertSpec._

  describe("A Alert") {

    it ("should able to parse friendship_request JSON") {

      val alert = Alert(friendshipRequest)

      alert.alertType should be === AlertType.FriendshipRequest
      alert.user.id should be === 7462357
      alert.posted should be === new Date(1361415361000L)
    }

    it ("should able to parse friendship_pending JSON") {

      val alert = Alert(friendshipPending)

      alert.alertType should be === AlertType.FriendshipPending
      alert.user.id should be === 1367985L
      alert.posted should be === new Date(1361418787000L)
    }

    it ("should able to parse friendship_accepted JSON") {
      val alert = Alert(friendshipAccepted)

      alert.alertType should be === AlertType.FriendshipAccepted
      alert.user.id should be === 1367985L
      alert.posted should be === new Date(1361415419000L)
    }

    it ("should able to parse new_fan JSON") {
      val alert = Alert(newFan)

      alert.alertType should be === AlertType.NewFan
      alert.user.id should be === 1534770L
      alert.posted should be === new Date(1361281431000L)
    }

    it ("should able to parse new_friend JSON") {
      val alert = Alert(newFriend)

      alert.alertType should be === AlertType.NewFriend
      alert.user.id should be === 7462357L
      alert.posted should be === new Date(1361415420000L)
    }

  }
}

object AlertSpec {

  val friendshipRequest = JsonParser.parse("""{
    "from_user": {
      "verified_account": false,
      "default_lang": "tr_ch",
      "dateformat": 0,
      "nick_name": "linuxhsu",
      "has_profile_image": 1,
      "location": "Taipei, Taiwan",
      "bday_privacy": 2,
      "date_of_birth": "Wed, 24 Nov 2010 00:00:00 GMT",
      "karma": 0,
      "full_name": "c d hw gee",
      "gender": 1,
      "timezone": null,
      "id": 7462357,
      "avatar": 2
    },
    "type": "friendship_request",
    "posted": "Thu, 21 Feb 2013 02:56:01 GMT"
  }""")

  val friendshipPending = JsonParser.parse("""{
    "to_user": {
      "verified_account": false,
      "default_lang": "tr_ch",
      "display_name": "\u58b3\u5893\uff08Brian Hsu\uff09",
      "dateformat": 0,
      "nick_name": "brianhsu",
      "has_profile_image": 1,
      "location": "Taipei, Taiwan",
      "bday_privacy": 2,
      "date_of_birth": "Tue, 02 Jan 1990 00:01:00 GMT",
      "karma": 121.16,
      "full_name": "BrianHsu",
      "gender": 1,
      "name_color": null,
      "timezone": "Asia\/Taipei",
      "id": 1367985,
      "avatar": 0
    },
    "type": "friendship_pending",
    "posted": "Thu, 21 Feb 2013 03:53:07 GMT"
  }""")

  val friendshipAccepted = JsonParser.parse("""{
    "friend_info": {
      "verified_account": false,
      "default_lang": "tr_ch",
      "display_name": "\u58b3\u5893\uff08Brian Hsu\uff09",
      "dateformat": 0,
      "nick_name": "brianhsu",
      "has_profile_image": 1,
      "location": "Taipei, Taiwan",
      "bday_privacy": 2,
      "date_of_birth": "Tue, 02 Jan 1990 00:01:00 GMT",
      "karma": 121.16,
      "full_name": "BrianHsu",
      "gender": 1,
      "name_color": null,
      "timezone": "Asia\/Taipei",
      "id": 1367985,
      "avatar": 0
    },
    "type": "friendship_accepted",
    "posted": "Thu, 21 Feb 2013 02:56:59 GMT"
  }""")

  val newFan = JsonParser.parse("""{
    "new_fan": {
      "verified_account": false,
      "default_lang": "tr_ch",
      "display_name": "Jimms",
      "dateformat": 0,
      "nick_name": "Jimms",
      "has_profile_image": 1,
      "location": "Taipei, Taiwan",
      "bday_privacy": 2,
      "date_of_birth": "Sun, 23 Nov 1986 00:01:00 GMT",
      "karma": 112.85,
      "full_name": "Jimms Hsieh",
      "gender": 1,
      "name_color": null,
      "timezone": null,
      "id": 1534770,
      "avatar": 0
    },
    "type": "new_fan",
    "posted": "Tue, 19 Feb 2013 13:43:51 GMT"
  }""")

  val newFriend = JsonParser.parse("""{
    "new_friend": {
      "verified_account": false,
      "default_lang": "tr_ch",
      "dateformat": 0,
      "nick_name": "linuxhsu",
      "has_profile_image": 1,
      "location": "Taipei, Taiwan",
      "bday_privacy": 2,
      "date_of_birth": "Wed, 24 Nov 2010 00:00:00 GMT",
      "karma": 0,
      "full_name": "c d hw gee",
      "gender": 1,
      "timezone": null,
      "id": 7462357,
      "avatar": 2
    },
    "type": "new_friend",
    "posted": "Thu, 21 Feb 2013 02:57:00 GMT"
  }""")


}


