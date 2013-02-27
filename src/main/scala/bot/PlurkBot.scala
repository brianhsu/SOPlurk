package org.bone.soplurk.bot

import akka.actor._

import org.bone.soplurk.api.PlurkAPI

import scala.util._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

/**
 *  PlurkBot
 *
 *  This bot will polling [[org.bone.soplurk.api.PlurkAPI.Alerts.getActive]] for every
 *  period defined at `alertsPollingPeriod` and pass it to handler, if there is any
 *  active alert.
 *
 *  It will also use [[org.bone.soplurk.api.PlurkAPI.Realtime]] to get new Plurks and
 *  responses, and pass them to handler.
 *
 *  You should use [[org.bone.soplurk.bot.PlurkBot.withPlurkAPI]] / 
 *  [[org.bone.soplurk.bot.PlurkBot.withAccessToken]] instead of create
 *  this object by your self.
 *
 *  We will pass the following three type of messages to handler:
 *
 *    - [[org.bone.soplurk.model.Alert]] for active alerts.
 *    - [[org.bone.soplurk.model.Plurk]] for new plurks.
 *    - [[org.bone.soplurk.model.RealtimeResponse]] for new responses.
 *
 *  @param  plurkAPI              The plurkAPI object, must be an authorized one.
 *  @param  alertsPollingPeroid   The peroid to polling alerts in seconds.
 *  @param  handler               The handler to handle message.
 */
class PlurkBot (plurkAPI: PlurkAPI, alertsPollingPeriod: Int = 60)
               (handler: BotAction) {

  require(plurkAPI.OAuthUtils.checkToken.isSuccess, "PlurkAPI must get authroized first")

  private val system = ActorSystem("PlurkBot")

  /**
   *  Actor that handle message from monitor actor.
   */
  private class BotActor(handler: PlurkAPI => Actor.Receive) extends Actor {
    def receive = handler(plurkAPI)
  }

  /**
   *  Start this PlurkBot
   */
  def start() {

    val botActorRef = system.actorOf(
      props = Props(new BotActor(handler)), 
      name = "bot"
    )

    val monitorActorRef = system.actorOf(
      props = Props(new MonitorActor(plurkAPI, botActorRef)), 
      name = "monitor"
    )

    system.scheduler.schedule(
      0.second, alertsPollingPeriod.second, 
      monitorActorRef, PollAlerts
    )

    plurkAPI.Realtime.getUserChannel.foreach { channel =>
      monitorActorRef ! GetRealtimeEvent(channel)
    }
  }

  /**
   *  Shutdown this bot
   */
  def shutdown() {
    system.shutdown()
  }

}

/**
 *  PlurkBot
 *
 *  You could use this object to create and start PlurkBot.
 *
 *  You could choose construct new PlurkAPI object by using appKey / appSecret and
 *  tokenKey / tokenSecret pair, or you could using a existing PlurkAPI object.
 *
 *  Please note that your plurkAPI must be authorized one, or it will throw exception.
 *
 *  Here is a minimal example code about how to use PlurkBot:
 *
 *  {{{
 *
 *    import org.bone.soplurk.api.PlurkAPI._
 *    import org.bone.soplurk.bot._
 *    import org.bone.soplurk.constant._
 *    import org.bone.soplurk.model._
 *
 *    val (appKey, appSecret) = ("APP_KEY", "APP_SECRET")
 *    val (tokenKey, tokenSecret) = ("TOKEN_KEY", "TOKEN_SECRET")
 *
 *    val plurkAPI = PlurkAPI.withAccessToken(appKey, appSecret, tokenKey, tokenSecret)
 *    val plurkBot = PlurkBot.withPlurkAPI(plurkAPI)( plurkAPI => {
 *      
 *      case alert: Alert => println("get alert:" + alert)
 *      case plurk: Plurk => println("get plurk:" + plurk)
 *      case response: RealtimeResponse => println("get response:" + response)
 *
 *    })
 *
 *  }}}
 *
 *
 */
object PlurkBot {
  
  /**
   *  Create and start Plurk bot by app / token pair.
   *
   *  This method will create an PlurkBot by using accessToken directly,
   *  and starts the bot after it is constructed.
   *
   *  @param    appKey                Your Plurk APP token
   *  @param    appSecret             Your Plurk APP secret
   *  @param    tokenKey              Your access token key.
   *  @param    tokenSecret           Your access token secret.
   *  @param    alertPollingPeroid    The peroid of polling active alerts in seconds.
   *  @param    handler               Your bot's action.
   *
   *  @return                         PlurkBot you constructed.
   */
  def withAccessToken(appKey: String, appSecret: String, 
                      tokenKey: String, tokenSecret: String,
                      alertPollingPeroid: Int = 60)(handler: BotAction) = {

    val plurkAPI = PlurkAPI.withAccessToken(appKey, appSecret, tokenKey, tokenSecret)
    val plurkBot = new PlurkBot(plurkAPI, alertPollingPeroid)(handler)
    plurkBot.start()
    plurkBot
  }

  /*
   *  Create and start Plurk bot using authorized PlurkAPI object.
   *
   *  This method will create an PlurkBot by using PlurkAPI object
   *  and starts the bot after it is constructed.
   *
   *
   *  @param    plurkAPI              The authroized PlurkAPI object.
   *  @param    alertPollingPeroid    The peroid of polling active alerts in seconds.
   *  @param    handler               Your bot's action.
   *
   *  @return                         PlurkBot you constructed.
   */

  def withPlurkAPI(plurkAPI: PlurkAPI, alertPollingPeroid: Int = 60)
                  (handler:BotAction): PlurkBot = {

    val plurkBot = new PlurkBot(plurkAPI, alertPollingPeroid)(handler)
    plurkBot.start()
    plurkBot
  }

}
