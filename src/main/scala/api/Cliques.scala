package org.bone.soplurk.api

import org.bone.soplurk.model._
import org.bone.soplurk.api.PlurkAPI._
import org.bone.soplurk.api.TimelineParser._

import org.scribe.model.Verb

import scala.util.Try
import java.util.Date

trait Cliques {

  this: PlurkAPI =>

  import MyJValueImplicits._

  /**
   *  API of /APP/Cliques/
   */
  object Cliques {

    /**
     *  Get list of cliques's name.
     *
     *  @return   The list of cliques if success.
     */
    def getCliques: Try[List[String]] = {
      
      val response = plurkOAuth.sendRequest("/APP/Cliques/getCliques", Verb.GET)

      response.map { jsonData => jsonData.children.map(_.values.toString) }

    }

    /**
     *  Get users list of a clique.
     *
     *  @param    name    The name of clique
     *  @return           Success[List[User]] that belongs to this clique if success.
     */
    def getClique(name: String): Try[List[User]] = {

      val response = plurkOAuth.sendRequest(
        "/APP/Cliques/getClique", Verb.GET,
        "clique_name" -> name
      )

      response.map { jsonData => 
        jsonData.children.map(userData => User(userData)) 
      }
    }

    /**
     *  Create a new clique
     *
     *  @param    name    The name of new clique.
     *  @return           Success[Boolean](true) if create clique successfully.
     */
    def createClique(name: String): Try[Boolean] = {

      val response = plurkOAuth.sendRequest(
        "/APP/Cliques/createClique", Verb.POST,
        "clique_name" -> name
      )

      response.map { jsonData => jsonData.get[String]("success_text") == "ok" }
    }

    /**
     *  Rename a clique
     *
     *  @param    name      Name of clique want to be renamed.
     *  @param    newName   New name of this clique.
     *  @return             Success[Boolean](true) if rename successfully.
     */
    def renameClique(name: String, newName: String): Try[Boolean] = {

      val response = plurkOAuth.sendRequest(
        "/APP/Cliques/renameClique", Verb.POST,
        "clique_name" -> name,
        "new_name" -> newName
      )

      response.map { jsonData => jsonData.get[String]("success_text") == "ok" }

    }

    /**
     *  Delete a clique
     *
     *  @param    name    The name of clique to be deleted.
     *  @return           Success[Boolean](true) if create clique successfully.
     */
    def deleteClique(name: String): Try[Boolean] = {

      val response = plurkOAuth.sendRequest(
        "/APP/Cliques/deleteClique", Verb.POST,
        "clique_name" -> name
      )

      response.map { jsonData => jsonData.get[String]("success_text") == "ok" }
    }

  }

}

