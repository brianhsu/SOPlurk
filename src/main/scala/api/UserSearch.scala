package org.bone.soplurk.api

import org.bone.soplurk.model._
import org.bone.soplurk.api.PlurkAPI._
import org.bone.soplurk.api.TimelineParser._

import org.scribe.model.Verb

import scala.util.Try
import java.util.Date

trait UserSearch {

  this: PlurkAPI =>

  import MyJValueImplicits._

  /**
   *  API of /APP/UserSearch/
   */
  object UserSearch {

    import TimelineParser._

    /**
     *  Search users
     *  
     *  This call will search users that matches `query`, return 10 users
     *  each time, you could using `offset` to get more users that matches
     *  the query.
     *
     *  The result user list will be sorted by karma.
     *
     *  @param  query   The query after users.
     *  @param  offset  Offset of user list, index starts from 0.
     *  
     *  @return         The search result.
     */
    def search(query: String, offset: Int = 0): Try[UserSearchResult] = {

      val response = plurkOAuth.sendRequest(
        "/APP/UserSearch/search", Verb.GET,
        "query" -> query,
        "offset" -> offset.toString
      )

      response.map { jsonData =>
        
        UserSearchResult(
          counts = jsonData.get("counts"),
          users  = (jsonData \ "users").children.map(userJson => User(userJson)),
          exactMatches = (jsonData \ "exact_matches").children.map(userJson => User(userJson))
        )

      }
    }

  }

}

