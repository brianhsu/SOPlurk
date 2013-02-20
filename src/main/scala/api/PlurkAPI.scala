package org.bone.soplurk.api

import org.bone.soplurk.model._
import org.bone.soplurk.oauth.PlurkOAuth
import org.bone.soplurk.oauth.MockOAuth

import org.scribe.builder._
import org.scribe.model.Token
import org.scribe.model.Verifier

import org.scribe.builder.api._

import scala.util.Try

import java.util.Date
import java.util.TimeZone
import java.text.SimpleDateFormat

class PlurkAPI private (val plurkOAuth: PlurkOAuth) extends Users with 
                Profile with Polling with Timeline with Responses with FriendsFans with 
                UserSearch with PlurkSearch with Cliques {

  private var requestToken: Option[Token] = None

  /**
   *  Get authorization URL.
   *
   *  @return   Success(url: String) if success, otherwise Failure[Exception].
   */
  def getAuthorizationURL: Try[String] = Try {
    val service = plurkOAuth.service
    requestToken = Some(service.getRequestToken)
    requestToken.map(service.getAuthorizationUrl).get
  }

  /**
   *  Authorize this PlurkAPI with verify code
   *
   *  You must call getAuthorizationURL and prompt / redirect user to
   *  that URL and get verify code before calling this method, 
   *  otherwise it will throw IllegalStateException.
   *
   *
   *  @param    verifyCode              Verify code returned by Plurk.
   *  @throws   IllegalStateException   if did not call getAuthorizationURL first.
   *  @throws   OAuthException          if authorization is failed.
   */
  def authorize(verifyCode: String) {

    if (requestToken.isEmpty) {
      throw new IllegalStateException(
        "You must call getAuthorizationURL before trying to authorize it"
      )
    }
      
    val service = plurkOAuth.service
    val verifier = new Verifier(verifyCode)
    val accessToken = Try(service.getAccessToken(requestToken.get, verifier))

    requestToken = None
    plurkOAuth.accessToken = accessToken.toOption
    accessToken.failed.foreach(exception => throw exception)
  }

}

object PlurkAPI {
  
  /**
   *  Get PlurkAPI with callback URL.
   *
   *  Create PlurkAPI object that user will be redirect to callbackURL
   *  when he verify this PlurkAPI client at Plurk.
   *
   *  @param    apiKey          API key from Plurk API console.
   *  @param    appSecret       API secret from Plurk API console.
   *  @param    callbackURL     OAuth callback URL.
   */
  def withCallback(apiKey: String, apiSecret: String, callbackURL: String) = {
    val service = (new ServiceBuilder).
                   provider(classOf[PlurkApi]).
                   apiKey(apiKey).
                   apiSecret(apiSecret).
                   callback(callbackURL).
                   build()
    
    new PlurkAPI(new PlurkOAuth(service))
  }

  /**
   *  Get PlurkAPI with callback URL.
   *
   *  Create PlurkAPI object that user need to input his verify code
   *  get from Plurk when he verified this PlurkAPI client at Plurk.
   *
   *  @param    apiKey          API key from Plurk API console.
   *  @param    appSecret       API secret from Plurk API console.
   */
  def withoutCallback(apiKey: String, apiSecret: String) = {
    val service = (new ServiceBuilder).
                   provider(classOf[PlurkApi]).
                   apiKey(apiKey).
                   apiSecret(apiSecret).
                   build()
    
    new PlurkAPI(new PlurkOAuth(service))
  }

  private[soplurk] def withMock(mockOAuth: PlurkOAuth with MockOAuth) = new PlurkAPI(mockOAuth)

  /**
   *  Format java.lang.Date object to `2009-6-20T21:55:34` in GMT timezone.
   *
   *  @param    date  Date object
   *  @return         The date time string formatted as Plurk expected.
   */
  private[soplurk] def toPlurkOffset(date: Date): String = {
    val formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    formatter.setTimeZone(TimeZone.getTimeZone("GMT"))
    formatter.format(date)
  }

  /**
   *  Represented data about specific Plurk.
   *
   *  @param    author  Information of this plurk's author.
   *  @param    users   Information of user that mentiond on this plurk.
   *  @param    plurk   Detail of this plurk.
   */
  case class PlurkData(author: User, users: Map[Long, User], plurk: Plurk)

  /**
   *  Represented whether replurk / unreplurk is success or failed.
   *
   *  @param    error   Error message if there is an error.
   *  @param    plurk   Data about this plurk.
   */
  case class ReplurkStatus(error: Option[String], plurk: Plurk) {
    val isSuccess = error.isEmpty
  }

  /*
   *  Represented the search result of a plurk searching.
   *
   *  @param  users       The user's information of found plurks.
   *  @param  plurks      Plurks that match the query.
   *  @param  hasMore     Do we have more plurks?
   *  @param  lastOffset  The last plurkID in this query.
   */
  case class PlurkSearchResult(
    users: Map[Long, User], plurks: List[Plurk], 
    hasMore: Boolean, lastOffset: Long
  )

  /**
   *  Represented the search result of a user searching.
   *
   *  @param  counts        How many users found.
   *  @param  users         Users found.
   *  @param  exactMatches  Users that is exact matches the query.
   */
  case class UserSearchResult(counts: Int, users: List[User], exactMatches: List[User])

  /**
   *  Represented data that need to render user's timeline
   *
   *  @param    users     A Map[userID, basicUserData], which key is user's ID.
   *  @param    plurks    Plurks on that timeline.
   */
  case class Timeline(users: Map[Long, User], plurks: List[Plurk])

  /**
   *  Unread plurks count
   *
   *  @param  all             Number of all unread plurks.
   *  @param  my              Number of unread plurks that is posted by current user.
   *  @param  privatePlurks   Number of unread plurks that is private plurk.
   *  @param  responded       Number of unread plurks that is responded by current user.
   *  @param  favorite        Number of unread favorite plurks that is favorited by user.
   */
  case class UnreadCount(
    all: Int, my: Int, privatePlurks: Int, 
    responded: Int, favorite: Int
  )

  /**
   *  Current user's profile.
   *
   *  @param  userInfo        User's information.
   *  @param  timeline        User's timeline and plurk posts.
   *  @param  fansCount       How many fans do you have.
   *  @param  friendsCount    How many friends do you have.
   *  @param  unreadCount     Number of unread plurks.
   *  @param  alertsCount     Number of unread alerts.
   *  @param  privacy         Your timeline privacy setting.
   */
  case class OwnProfile(
    userInfo: ExtendedUser, timeline: Timeline, 
    fansCount: Int, friendsCount: Int, 
    unreadCount: Int, alertsCount: Int,
    privacy: TimelinePrivacy
  )

  /**
   *  User's public profile.
   *
   *  @param  userInfo            User's information
   *  @param  plurks              Plurks that posted by the user.
   *  @param  fansCount           How many fans does this user has.
   *  @param  friendsCount        How many friends does this user has.
   *  @param  privacy             This user's privacy setting.
   *  @param  hasReadPermission   Do you have permission to read this user's plurk.
   *  @param  isFan               Are you a fan of this user? Only set if logged in.
   *  @param  areFriends          Are you a friend of this user? Only set if logged in.
   *  @param  isFollowing         Are you following this user? Only set if logged in.
   */
  case class PublicProfile(
    userInfo: ExtendedUser, plurks: List[Plurk],
    fansCount: Int, friendsCount: Int, 
    privacy: TimelinePrivacy, hasReadPermission: Boolean,
    isFan: Option[Boolean],
    areFriends: Option[Boolean],
    isFollowing: Option[Boolean]
  )

  /**
   *  Represented responses of specific Plurk.
   *
   *  The `friends` field is a `Map[Long, User]`, where key is the userID of users
   *  that posted responses, and value is the user information about that user.
   * 
   *  @param  friends     The user information about users that has posted response.
   *  @param  responses   Responses of this plurk.
   *  @param  seen        The last response that logged user has seen.
   *                      0 means all unread, -1 means there is no response.
   */
  case class PlurkResponses(friends: Map[Long, User], responses: List[Response], seen: Int)

}

