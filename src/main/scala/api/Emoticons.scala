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

  }

}

