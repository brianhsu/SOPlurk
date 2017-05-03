package org.bone.soplurk.bot

import akka.actor._

import org.bone.soplurk.api.PlurkAPI
import org.bone.soplurk.api.PlurkAPI.UserChannel
import org.bone.soplurk.api.PlurkAPI.RealtimeEvent
import org.bone.soplurk.model.Alert

import scala.util._
import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global


/**
 *  PlurkBot Monitor Actor
 *
 *  This actor will keep polling data from Plurk Realtime channel and
 *  [[org.bone.soplurk.PlurkAPI.Alerts.getActive]], and pass those events
 *  to BotActor.
 */
private class MonitorActor(plurkAPI: PlurkAPI, bot: ActorRef) extends Actor {
  
  private def getAlerts: Future[List[Alert]] = { 
    Future(plurkAPI.Alerts.getActive getOrElse Nil)
  }

  private def getEvents(channel: UserChannel): Future[Try[RealtimeEvent]] = {
    Future(plurkAPI.Realtime.getEvents(channel))
  }

  /**
   *  Get events from Plurk Realtime Channel and sent it to BotActor
   *
   *  @param  channel   The Plurk realtime channel.
   */
  def sendRealtimeEvent(channel: UserChannel)
  {
      getEvents(channel).foreach { futureEvents =>

        // If we get event correctly
        futureEvents.foreach { case RealtimeEvent(nextChannel, events) =>
          
          // Send it to BotActor
          events.foreach { event => 
            event match {
              case Left(plurk) => bot ! plurk
              case Right(response) => bot ! response
            }
          }

          // And try to get data from next channel
          self ! GetRealtimeEvent(nextChannel)
        }

        // Otherwise, if failed, try to get data from
        // current channel again.
        futureEvents.failed.foreach { exception =>
          self ! GetRealtimeEvent(channel)
        }
      }
  }

  /**
   *  Sends alert event to BotActor.
   *
   *  @param  alerts    Alerts to be sent.
   */
  def sendAlerts(alerts: List[Alert]) {
    alerts.foreach(alert => bot ! alert )
  }

  def receive = {
    case PollAlerts => getAlerts.foreach(sendAlerts _)
    case GetRealtimeEvent(channel) => sendRealtimeEvent(channel)
  }

}

