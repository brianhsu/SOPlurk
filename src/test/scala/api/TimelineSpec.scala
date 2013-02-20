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


object TimelineAPIMock extends PlurkOAuth(null) with MockOAuth {

  val getPlurkResponse = JsonParser.parse("""{
    "plurk_users": {
        "4460064": {
            "verified_account": false,
            "default_lang": "tr_ch",
            "display_name": "Users",
            "dateformat": 0,
            "nick_name": "platina6014",
            "has_profile_image": 1,
            "location": "Fatimaid, Taiwan",
            "bday_privacy": 2,
            "date_of_birth": "Mon, 15 Jun 1987 00:01:00 GMT",
            "karma": 114.64,
            "full_name": "Users",
            "gender": 0,
            "name_color": null,
            "timezone": null,
            "id": 4460064,
            "avatar": 86
        }
    },
    "user": {
        "verified_account": false,
        "default_lang": "tr_ch",
        "display_name": "吉他 Tsubaki",
        "dateformat": 0,
        "nick_name": "platina6014",
        "has_profile_image": 1,
        "location": "Fatimaid, Taiwan",
        "bday_privacy": 2,
        "date_of_birth": "Mon, 15 Jun 1987 00:01:00 GMT",
        "karma": 114.64,
        "full_name": "Users",
        "gender": 0,
        "name_color": null,
        "timezone": null,
        "id": 4460064,
        "avatar": 86
    },
    "plurk": {
        "replurkers_count": 0,
        "replurkable": true,
        "favorite_count": 3,
        "is_unread": 0,
        "favorers": [
            4426947,
            5658139,
            8427086
        ],
        "user_id": 4460064,
        "plurk_type": 0,
        "qualifier_translated": "說",
        "replurked": false,
        "content": "QQQQ",
        "replurker_id": null,
        "owner_id": 4460064,
        "responses_seen": 0,
        "qualifier": "says",
        "plurk_id": 1099209841,
        "response_count": 8,
        "limited_to": null,
        "no_comments": 0,
        "posted": "Tue, 19 Feb 2013 01:41:05 GMT",
        "lang": "tr_ch",
        "content_raw": "RawContent",
        "replurkers": [],
        "favorite": false
    }
  }""")

  val getPlurksResponse = JsonParser.parse("""{
    "plurk_users": {
        "8290019": {
            "verified_account": false,
            "default_lang": "tr_ch",
            "display_name": "YUME",
            "dateformat": 0,
            "nick_name": "likerm6",
            "has_profile_image": 1,
            "location": "Tapei, Taiwan",
            "bday_privacy": 2,
            "date_of_birth": "Fri, 14 Jun 1996 00:01:00 GMT",
            "karma": 100.4,
            "full_name": "Yume 夢夢",
            "gender": 0,
            "name_color": "0A9C17",
            "timezone": null,
            "id": 8290019,
            "avatar": 54
        },
        "3833577": {
            "verified_account": false,
            "default_lang": "tr_ch",
            "display_name": "mingwangx",
            "dateformat": 0,
            "nick_name": "mingwangx",
            "has_profile_image": 1,
            "location": "淡水, Taiwan",
            "bday_privacy": 2,
            "date_of_birth": "Fri, 08 Dec 1978 00:01:00 GMT",
            "karma": 125.34,
            "full_name": "鐘銘",
            "gender": 1,
            "name_color": "0A9C17",
            "timezone": null,
            "id": 3833577,
            "avatar": 70
        }
    },
    "plurks": [
        {
            "replurkers_count": 4,
            "replurkable": true,
            "favorite_count": 4,
            "is_unread": 0,
            "content": "Content",
            "user_id": 1367985,
            "plurk_type": 0,
            "qualifier_translated": "分享",
            "replurked": false,
            "favorers": [
                3898769,
                5220104,
                6053156,
                7241580
            ],
            "replurker_id": null,
            "owner_id": 5530231,
            "responses_seen": 0,
            "qualifier": "shares",
            "plurk_id": 1098983626,
            "response_count": 38,
            "limited_to": null,
            "no_comments": 0,
            "posted": "Mon, 18 Feb 2013 09:05:30 GMT",
            "lang": "tr_ch",
            "content_raw": "Content",
            "replurkers": [
                5608888,
                5618185,
                7241580,
                8296926
            ],
            "favorite": false
        },
        {
            "replurkers_count": 0,
            "replurkable": true,
            "favorite_count": 0,
            "is_unread": 1,
            "content": "Content",
            "user_id": 1367985,
            "plurk_type": 0,
            "qualifier_translated": "說",
            "replurked": false,
            "favorers": [],
            "replurker_id": null,
            "owner_id": 5663569,
            "responses_seen": 0,
            "qualifier": "says",
            "plurk_id": 1098974413,
            "response_count": 1,
            "limited_to": null,
            "no_comments": 0,
            "posted": "Mon, 18 Feb 2013 08:26:40 GMT",
            "lang": "tr_ch",
            "content_raw": "Content",
            "replurkers": [],
            "favorite": false
        },
        {
            "replurkers_count": 0,
            "replurkable": true,
            "favorite_count": 0,
            "is_unread": 0,
            "content": "Content",
            "user_id": 1367985,
            "plurk_type": 0,
            "qualifier_translated": "說",
            "replurked": false,
            "favorers": [],
            "replurker_id": null,
            "owner_id": 3648151,
            "responses_seen": 0,
            "qualifier": "says",
            "plurk_id": 1098944322,
            "response_count": 4,
            "limited_to": null,
            "no_comments": 0,
            "posted": "Mon, 18 Feb 2013 06:11:16 GMT",
            "lang": "tr_ch",
            "content_raw": "Content",
            "replurkers": [],
            "favorite": false
        }
    ]
  }""")

  val getUnreadPlurksResponse = JsonParser.parse("""{
    "plurk_users": {
        "5530231": {
            "verified_account": true,
            "default_lang": "tr_ch",
            "display_name": "跳洞王．賊毛子",
            "dateformat": 0,
            "nick_name": "johnnydoki",
            "has_profile_image": 1,
            "location": "Taipei, Taiwan",
            "bday_privacy": 2,
            "date_of_birth": "Mon, 14 Jul 1986 00:01:00 GMT",
            "karma": 96.07,
            "full_name": "城門城門睫毛糕",
            "gender": 1,
            "name_color": null,
            "timezone": null,
            "id": 5530231,
            "avatar": 10
        },
        "5563305": {
            "verified_account": false,
            "default_lang": "tr_ch",
            "display_name": "Prydz狂熱份子♥紫戀戀",
            "dateformat": 0,
            "nick_name": "shlian",
            "has_profile_image": 1,
            "location": "變態怪姊姊流放區, Taiwan",
            "bday_privacy": 2,
            "date_of_birth": "Thu, 09 Apr 1992 00:01:00 GMT",
            "karma": 117.64,
            "full_name": "四条 紫恋(Ren)",
            "gender": 0,
            "name_color": "0A9C17",
            "timezone": null,
            "id": 5563305,
            "avatar": 100
        },
        "8290019": {
            "verified_account": false,
            "default_lang": "tr_ch",
            "display_name": "YUME",
            "dateformat": 0,
            "nick_name": "likerm6",
            "has_profile_image": 1,
            "location": "Tapei, Taiwan",
            "bday_privacy": 2,
            "date_of_birth": "Fri, 14 Jun 1996 00:01:00 GMT",
            "karma": 100.43,
            "full_name": "Yume 夢夢",
            "gender": 0,
            "name_color": "0A9C17",
            "timezone": null,
            "id": 8290019,
            "avatar": 54
        },
        "4065129": {
            "verified_account": false,
            "default_lang": "tr_ch",
            "display_name": "lordmi",
            "dateformat": 0,
            "nick_name": "lordmi",
            "has_profile_image": 1,
            "location": "Chungho, Taiwan",
            "bday_privacy": 2,
            "date_of_birth": "Fri, 11 May 1973 00:01:00 GMT",
            "karma": 132.97,
            "full_name": "星宿喵",
            "gender": 1,
            "name_color": "2264D6",
            "timezone": "Asia/Taipei",
            "id": 4065129,
            "avatar": 2
        }
    },
    "plurks": [
        {
            "replurkers_count": 0,
            "replurkable": false,
            "favorite_count": 0,
            "is_unread": 1,
            "favorers": [],
            "user_id": 1367985,
            "plurk_type": 0,
            "qualifier_translated": "說",
            "replurked": false,
            "content": "[仙劍]<br />可惡我對前傳的愛要超過五代了 <img class=\"emoticon_my\" src=\"http://emos.plurk.com/9fa6a3506ed34e918de7094d1f13d838_w48_h47.jpeg\" width=\"48\" height=\"47\" />",
            "replurker_id": null,
            "owner_id": 5563305,
            "responses_seen": 3,
            "qualifier": "says",
            "plurk_id": 1099278440,
            "response_count": 4,
            "limited_to": null,
            "no_comments": 0,
            "posted": "Tue, 19 Feb 2013 07:01:45 GMT",
            "lang": "tr_ch",
            "content_raw": "[仙劍]\n可惡我對前傳的愛要超過五代了[表情_37]",
            "replurkers": [],
            "favorite": false
        },
        {
            "replurkers_count": 0,
            "replurkable": true,
            "favorite_count": 0,
            "is_unread": 1,
            "content": "放假放太久回去餐車就一直做出好笑的事情XDDDD",
            "user_id": 1367985,
            "plurk_type": 0,
            "qualifier_translated": "說",
            "replurked": false,
            "favorers": [],
            "replurker_id": null,
            "owner_id": 8290019,
            "responses_seen": 7,
            "qualifier": "says",
            "plurk_id": 1099146091,
            "response_count": 8,
            "limited_to": null,
            "no_comments": 0,
            "posted": "Mon, 18 Feb 2013 17:19:53 GMT",
            "lang": "tr_ch",
            "content_raw": "放假放太久回去餐車就一直做出好笑的事情XDDDD",
            "replurkers": [],
            "favorite": false
        }
    ]
  }""")

  val getPublicPlurksResponse = JsonParser.parse("""{
    "plurk_users": {
        "1367985": {
            "verified_account": false,
            "default_lang": "tr_ch",
            "display_name": "墳墓（Brian Hsu）",
            "dateformat": 0,
            "nick_name": "brianhsu",
            "has_profile_image": 1,
            "location": "Taipei, Taiwan",
            "bday_privacy": 2,
            "date_of_birth": "Tue, 02 Jan 1990 00:01:00 GMT",
            "karma": 121.1,
            "full_name": "BrianHsu",
            "gender": 1,
            "name_color": null,
            "timezone": "Asia/Taipei",
            "id": 1367985,
            "avatar": 0
        },
        "3352030": {
            "verified_account": false,
            "default_lang": "fr",
            "display_name": "L69桃子*",
            "dateformat": 0,
            "nick_name": "community666",
            "has_profile_image": 1,
            "location": "élysée, France",
            "bday_privacy": 0,
            "date_of_birth": null,
            "karma": 104.08,
            "full_name": "降靈科☆午後九時Don't be late",
            "gender": 2,
            "name_color": "2264D6",
            "timezone": null,
            "id": 3352030,
            "avatar": 64
        }
    },
    "plurks": [
        {
            "responses_seen": 0,
            "qualifier": "says",
            "replurkers": [],
            "plurk_id": 1099225928,
            "response_count": 0,
            "replurkers_count": 0,
            "replurkable": true,
            "limited_to": null,
            "no_comments": 0,
            "favorite_count": 0,
            "is_unread": 0,
            "lang": "tr_ch",
            "favorers": [],
            "content_raw": "[Note] 噗浪的 API 回傳的 limited_to 有陷阱，他的格式是 \"limited_to\": \"|1367985||8814877||4373060|\" 而不是普通的 json 陣列啊……",
            "user_id": 1367985,
            "plurk_type": 0,
            "qualifier_translated": "說",
            "replurked": false,
            "favorite": false,
            "content": "[Note] 噗浪的 API 回傳的 limited_to 有陷阱，他的格式是 \"limited_to\": \"|1367985||8814877||4373060|\" 而不是普通的 json 陣列啊……",
            "replurker_id": null,
            "posted": "Tue, 19 Feb 2013 02:57:14 GMT",
            "owner_id": 1367985
        },
        {
            "responses_seen": 0,
            "qualifier": "says",
            "replurkers": [
                3350806
            ],
            "plurk_id": 1099207287,
            "response_count": 2,
            "replurkers_count": 1,
            "replurkable": true,
            "limited_to": null,
            "no_comments": 0,
            "favorite_count": 2,
            "is_unread": 0,
            "lang": "tr_ch",
            "favorers": [
                4268269,
                8038475
            ],
            "content_raw": "http://shouhei-blog.blogspot.tw/2013/02/blog-post_7.html 我覺得這比之前小叮噹那個還好笑 XDDDD",
            "user_id": 1367985,
            "plurk_type": 2,
            "qualifier_translated": "說",
            "replurked": false,
            "favorite": false,
            "content": "<a href=\"http://shouhei-blog.blogspot.tw/2013/02/blog-post_7.html\" class=\"ex_link meta\" rel=\"nofollow\"><img src=\"http://3.bp.blogspot.com/-lD0tQOktF-4/UPI3dqZNH3I/AAAAAAAAHbw/FLafcEcjMx8/s72-c/7e66ff36.jpg\" height=\"40px\" />新注音新聞: &#12304;用安價來讓這兩個人變成幸福的情侶吧&#12305;</a> 我覺得這比之前小叮噹那個還好笑 XDDDD",
            "replurker_id": null,
            "posted": "Tue, 19 Feb 2013 01:29:52 GMT",
            "owner_id": 1367985
        },
        {
            "responses_seen": 0,
            "qualifier": "says",
            "replurkers": [
                3350806
            ],
            "plurk_id": 1099207287,
            "response_count": 2,
            "replurkers_count": 1,
            "replurkable": true,
            "limited_to": null,
            "no_comments": 0,
            "favorite_count": 2,
            "is_unread": 0,
            "lang": "tr_ch",
            "favorers": [
                4268269,
                8038475
            ],
            "content_raw": "http://shouhei-blog.blogspot.tw/2013/02/blog-post_7.html 我覺得這比之前小叮噹那個還好笑 XDDDD",
            "user_id": 1367985,
            "plurk_type": 2,
            "qualifier_translated": "說",
            "replurked": false,
            "favorite": false,
            "content": "<a href=\"http://shouhei-blog.blogspot.tw/2013/02/blog-post_7.html\" class=\"ex_link meta\" rel=\"nofollow\"><img src=\"http://3.bp.blogspot.com/-lD0tQOktF-4/UPI3dqZNH3I/AAAAAAAAHbw/FLafcEcjMx8/s72-c/7e66ff36.jpg\" height=\"40px\" />新注音新聞: &#12304;用安價來讓這兩個人變成幸福的情侶吧&#12305;</a> 我覺得這比之前小叮噹那個還好笑 XDDDD",
            "replurker_id": null,
            "posted": "Tue, 19 Feb 2013 01:29:52 GMT",
            "owner_id": 1367985
        }

    ]
  }""")

  val plurkAddResponse = JsonParser.parse("""{
    "replurkers": [],
    "responses_seen": 0,
    "qualifier": "thinks",
    "replurkers_count": 0,
    "plurk_id": 1099313284,
    "response_count": 0,
    "anonymous": false,
    "replurkable": true,
    "limited_to": null,
    "favorite_count": 0,
    "is_unread": 0,
    "lang": "en",
    "favorers": [],
    "content_raw": "plurkAddTest",
    "user_id": 1367985,
    "plurk_type": 0,
    "replurked": false,
    "favorite": false,
    "no_comments": 0,
    "content": "plurkAddTest",
    "replurker_id": null,
    "posted": "Tue, 19 Feb 2013 09:38:22 GMT",
    "owner_id": 1367985
  }""")

  val plurkEditResponse = JsonParser.parse("""{
    "replurkers": [],
    "responses_seen": 0,
    "qualifier": "thinks",
    "replurkers_count": 0,
    "plurk_id": 1099313284,
    "response_count": 0,
    "anonymous": false,
    "replurkable": true,
    "limited_to": null,
    "favorite_count": 0,
    "is_unread": 0,
    "lang": "en",
    "favorers": [],
    "content_raw": "plurkEditContent",
    "user_id": 1367985,
    "plurk_type": 0,
    "replurked": false,
    "favorite": false,
    "no_comments": 0,
    "content": "plurkEditContent",
    "replurker_id": null,
    "posted": "Tue, 19 Feb 2013 09:38:22 GMT",
    "owner_id": 1367985
  }""")

  val replurkResponse = JsonParser.parse("""{
    "results": {
        "1098776249": {
            "plurk": {
                "replurkers": [
                    1367985
                ],
                "responses_seen": 0,
                "qualifier": "shares",
                "replurkers_count": 1,
                "replurker_id": null,
                "response_count": 1,
                "replurkable": true,
                "limited_to": null,
                "id": 1098776249,
                "favorite_count": 0,
                "is_unread": 0,
                "lang": "tr_ch",
                "content": "Content",
                "content_raw": "ContentRaw",
                "user_id": 8120915,
                "plurk_type": 0,
                "replurked": true,
                "longitude": null,
                "no_comments": 0,
                "favorers": [],
                "plurk_id": 1098776249,
                "latitude": null,
                "posted": "Sun, 17 Feb 2013 15:20:30 GMT",
                "owner_id": 8120915
            },
            "success": true,
            "error": ""
        },
        "1098983626": {
            "plurk": {
                "replurkers": [
                    9342645,
                    1367985
                ],
                "responses_seen": 0,
                "qualifier": "shares",
                "replurkers_count": 51,
                "replurker_id": "",
                "response_count": 1255,
                "replurkable": true,
                "limited_to": null,
                "id": 1098983626,
                "favorite_count": 85,
                "is_unread": 0,
                "lang": "tr_ch",
                "favorers": [9325158, 9342645],
                "content_raw": "Content",
                "user_id": 5530231,
                "plurk_type": 0,
                "replurked": true,
                "longitude": null,
                "no_comments": 0,
                "content": "Content",
                "plurk_id": 1098983626,
                "latitude": null,
                "posted": "Mon, 18 Feb 2013 09:05:30 GMT",
                "owner_id": 5530231
            },
            "success": true,
            "error": ""
        }
    },
    "success": true
  }""")

  val unreplurkResponse = replurkResponse

  val plurkDeleteResponse = JsonParser.parse("""{"success_text": "ok"}""")
  val mutePlurksResponse = JsonParser.parse("""{"success_text": "ok"}""")
  val unmutePlurksResponse = JsonParser.parse("""{"success_text": "ok"}""")
  val favoritePlurksResponse = JsonParser.parse("""{"success_text": "ok"}""")
  val unfavoritePlurksResponse = JsonParser.parse("""{"success_text": "ok"}""")
  val markAsReadResponse = JsonParser.parse("""{"success_text": "ok"}""")

  override def sendRequest(url: String, method: Verb, 
                           params: (String, String)*): Try[JValue] = {

    def hasPlurkIDs(ids: List[Long]) = params.contains("ids" -> ids.mkString("[", ",", "]"))
    def hasContent(content: String) = params.contains("content" -> "plurkEditContent")
    def hasPlurkID(id: Long) = params.contains("plurk_id" -> id.toString)
    def hasNickname(nickname: String) = params.contains("user_id" -> nickname)
    def hasAdd(content: String, qualifier: String) = {
      params.contains("content" -> content) &&
      params.contains("qualifier" -> qualifier)
    }

    (url, method) match {
      case ("/APP/Timeline/getPlurk", Verb.GET) if hasPlurkID(1099209841L) => 
        Success(getPlurkResponse)

      case ("/APP/Timeline/getPlurks", Verb.GET) => 
        Success(getPlurksResponse)

      case ("/APP/Timeline/getUnreadPlurks", Verb.GET) => 
        Success(getUnreadPlurksResponse)

      case ("/APP/Timeline/getPublicPlurks", Verb.GET) if hasNickname("brianhsu") => 
        Success(getPublicPlurksResponse)

      case ("/APP/Timeline/plurkAdd", Verb.POST) if hasAdd("plurkAddTest", "thinks") =>
        Success(plurkAddResponse)

      case ("/APP/Timeline/plurkDelete", Verb.POST) if hasPlurkID(1234L) =>
        Success(plurkDeleteResponse)

      case ("/APP/Timeline/plurkEdit", Verb.POST) if hasPlurkID(1099313284L) && 
                                                     hasContent("plurkEditContent") =>
        Success(plurkEditResponse)

      case ("/APP/Timeline/mutePlurks", Verb.POST) if hasPlurkIDs(List(324L, 23242L, 2323L)) =>
        Success(mutePlurksResponse)

      case ("/APP/Timeline/unmutePlurks", Verb.POST) if hasPlurkIDs(List(324L, 23242L, 2323L)) =>
        Success(unmutePlurksResponse)

      case ("/APP/Timeline/favoritePlurks", Verb.POST) if hasPlurkIDs(List(324L, 23242L, 2323L)) =>
        Success(favoritePlurksResponse)

      case ("/APP/Timeline/unfavoritePlurks", Verb.POST) if hasPlurkIDs(List(324L, 23242L, 2323L)) =>
        Success(unfavoritePlurksResponse)

      case ("/APP/Timeline/markAsRead", Verb.POST) if hasPlurkIDs(List(324L, 23242L, 2323L)) =>
        Success(markAsReadResponse)

      case ("/APP/Timeline/replurk", Verb.POST) if hasPlurkIDs(List(1098776249L, 1098983626L)) =>
        Success(replurkResponse)

      case ("/APP/Timeline/unreplurk", Verb.POST) if hasPlurkIDs(List(1098776249L, 1098983626L)) =>
        Success(unreplurkResponse)

      case _ => 
        Failure(throw new Exception("Not implemented"))
    }

  }

}

class TimelineSpec extends FunSpec with ShouldMatchers {

  describe("A PlurkAPI with Timeline trait") {

    val plurkAPI = PlurkAPI.withMock(TimelineAPIMock)

    it ("get specific plurk by /APP/Timeline/getPlurk correctly") {

      val PlurkData(author, users, plurk) = 
        plurkAPI.Timeline.getPlurk(1099209841L).get

      author.id should be === 4460064
      users.get(4460064L).map(_.id) should be === Some(4460064L)
      plurk.plurkID should be === 1099209841L
      plurk.contentRaw should be === Some("RawContent")
    }

    it ("get plurks by /APP/Timeline/getPlurks correctly") {

      val Timeline(users, plurks) = plurkAPI.Timeline.getPlurks().get

      users.size should be === 2
      plurks.size should be === 3

    }

    it ("get unread plurks by /APP/Timeline/getUnreadPlurks correctly") {

      val Timeline(users, plurks) = plurkAPI.Timeline.getUnreadPlurks().get

      users.size should be === 4
      plurks.size should be === 2

    }

    it ("get public plurks by /APP/Timelin/getPublicPlurks correctly") {

      val Timeline(users, plurks) = plurkAPI.Timeline.getPublicPlurks("brianhsu").get

      users.size should be === 2
      plurks.size should be === 3

    }

    it ("add plurks by /APP/Timeline/plurkAdd correctly") {

      val plurk = plurkAPI.Timeline.plurkAdd("plurkAddTest", Qualifier.Thinks).get
      
      plurk.plurkID should be === 1099313284
      plurk.content should be === "plurkAddTest"
      plurk.qualifier should be === Qualifier.Thinks

    }

    it ("delete plurk by /APP/Timeline/plurkDelete correctly") {

      val isOK = plurkAPI.Timeline.plurkDelete(1234L).get
      isOK should be === true

    }

    it ("edit plurk by /APP/Timeline/plurkEdit correctly") {

      val plurk = plurkAPI.Timeline.plurkEdit(1099313284L, "plurkEditContent").get
      
      plurk.plurkID should be === 1099313284L
      plurk.content should be === "plurkEditContent"

    }

    it ("mute plurks by /APP/Timeline/mutePlurks correctly") {

      val plurkIDs = List(324L, 23242L, 2323L)
      val isOK = plurkAPI.Timeline.mutePlurks(plurkIDs).get
      isOK should be === true

    }

    it ("unmute plurks by /APP/Timeline/unmutePlurks correctly") {

      val plurkIDs = List(324L, 23242L, 2323L)
      val isOK = plurkAPI.Timeline.unmutePlurks(plurkIDs).get
      isOK should be === true

    }

    it ("favorite plurks by /APP/Timeline/favoritePlurks correctly") {

      val plurkIDs = List(324L, 23242L, 2323L)
      val isOK = plurkAPI.Timeline.favoritePlurks(plurkIDs).get
      isOK should be === true

    }

    it ("unfavorite plurks by /APP/Timeline/unfavoritePlurks correctly") {

      val plurkIDs = List(324L, 23242L, 2323L)
      val isOK = plurkAPI.Timeline.unfavoritePlurks(plurkIDs).get
      isOK should be === true

    }

    it ("mark plurks as read by /APP/Timeline/markAsRead correctly") {

      val plurkIDs = List(324L, 23242L, 2323L)
      val isOK = plurkAPI.Timeline.markAsRead(plurkIDs).get
      isOK should be === true

    }

    it ("should replurk by /APP/Timeline/replurk correctly") {

      val (isSuccess, replurkStatus) = plurkAPI.Timeline.replurk(1098776249L :: 1098983626L :: Nil).get

      isSuccess should be === true
      replurkStatus.size should be === 2
      replurkStatus(1098776249L).isSuccess should be === true
      replurkStatus(1098983626L).isSuccess should be === true
      replurkStatus(1098983626L).plurk.plurkID should be === 1098983626L
      replurkStatus(1098776249L).plurk.plurkID should be === 1098776249L
    }


    it ("should unreplurk by /APP/Timeline/replurk correctly") {

      val (isSuccess, replurkStatus) = plurkAPI.Timeline.unreplurk(1098776249L :: 1098983626L :: Nil).get

      isSuccess should be === true
      replurkStatus.size should be === 2
      replurkStatus(1098776249L).isSuccess should be === true
      replurkStatus(1098983626L).isSuccess should be === true
      replurkStatus(1098983626L).plurk.plurkID should be === 1098983626L
      replurkStatus(1098776249L).plurk.plurkID should be === 1098776249L
    }

  }
}

