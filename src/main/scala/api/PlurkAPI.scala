package org.bone.soplurk.api

import org.bone.soplurk.model._
import org.bone.soplurk.oauth.PlurkOAuth
import org.bone.soplurk.oauth.MockOAuth

import org.scribe.builder._
import org.scribe.model._
import org.scribe.builder.api._

import scala.util.Try

class PlurkAPI private (val plurkOAuth: PlurkOAuth) extends Users with Profile with Polling {

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

}

