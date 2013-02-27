package org.bone.soplurk.model

import net.liftweb.json.JValue

/**
 *  Represented as a Topic in PlurkTop.
 *
 *  @param  id              The unique id of this topic.
 *  @param  translatedName  The name of this topic in local language.
 *  @param  name            The english name of this topic.
 */
case class Topic(id: Int, translatedName: String, name: String)

object Topic {

  /**
   *  Create Topic object from JSON dta.
   *
   *  @param  jsonData  JSON returned by Plurk.
   *  @return           Topic object corresponding to jsonData.
   */
  def apply(jsonData: JValue): Topic = {
    
    Topic(
      id = jsonData(0).values.toString.toInt,
      translatedName = jsonData(1).values.toString,
      name = jsonData(2).values.toString
    )

  }

}

