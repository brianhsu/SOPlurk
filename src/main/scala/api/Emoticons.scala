package org.bone.soplurk.api

import org.bone.soplurk.model._
import org.bone.soplurk.api.PlurkAPI._
import org.bone.soplurk.api.TimelineParser._

import org.scribe.model.Verb

import net.liftweb.json.JsonAST._
import scala.util.Try

trait Emoticons {

  this: PlurkAPI =>

  import MyJValueImplicits._

  /**
   *  API of /APP/Emoticons/
   */
  object Emoticons {
    
    /**
     *  Parse icons map in Plurk JSON.
     *
     *  @param    jsonData  JSON data of icons map.
     *  @return             The lower bound / icons map.
     */
    private def parseIconsMap(jsonData: JValue): Map[Int, List[Emoticon]] = {

      jsonData.children.map { data =>

        // The key in JSON entry is the lower bound of recruited count / karma.
        val count = data.asInstanceOf[JField].name.toInt

        // The value in JSON entry is an List[List[String] which has 2 element]
        val fieldValue = data.asInstanceOf[JField].value

        // So we maps the inner Array[String] to Emoticon, which will result
        // to List[Emoticon].
        val icons = fieldValue.children.map(icon => Emoticon(icon))

        (count -> icons)
      }.toMap
    }
    
    /**
     *  Get usable Emoticons from Plurk.
     *
     *  @return   Success[EmoticonsList] if everything is OK.
     */
    def get: Try[EmoticonsList] = {

      val response = plurkOAuth.sendRequest("/APP/Emoticons/get", Verb.GET)

      response.map { jsonData =>
        
        val customIcons = (jsonData \ "custom").children.map(icon => CustomIcon(icon))
        val recruitedIcons = parseIconsMap(jsonData \ "recruited")
        val karmaIcons = parseIconsMap(jsonData \ "karma")

        EmoticonsList(customIcons, recruitedIcons, karmaIcons)
      }

    }

    /**
     *  Add custom emoticons from URL.
     *
     *  This API only support url emoticons that are uploaded to Plurk already,
     *  which meas the `url` must starts with `http://emos.plurk.com/`.
     *
     *  @param    url       The url of custom emoticon icon.
     *  @param    keyword   The keyword (name) of this icon.
     *
     *  @return             Success[Boolean](true) if add icon successfuly.
     */
    def addFromURL(url: String, keyword: String): Try[Boolean] = {

      val response = plurkOAuth.sendRequest(
        "/APP/Emoticons/addFromURL", Verb.POST,
        "url" -> url,
        "keyword" -> keyword
      )

      response.map { jsonData => jsonData.get[String]("success_text") == "ok" }
    }

    /**
     *  Delete custom emoticon icon
     *
     *  @param    keyword   The keyword (name) of custom icon to be deleted.
     *  @return             Success[Boolean](true) if delete icon successfuly.
     */
    def delete(keyword: String): Try[Boolean] = {

      val response = plurkOAuth.sendRequest(
        "/APP/Emoticons/delete", Verb.POST,
        "keyword" -> keyword
      )

      response.map { jsonData => jsonData.get[String]("success_text") == "ok" }
    }

  }

}

