package org.bone.soplurk.api

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

  def withMock(mockOAuth: PlurkOAuth with MockOAuth) = new PlurkAPI(mockOAuth)

}

