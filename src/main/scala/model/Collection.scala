package org.bone.soplurk.model

import net.liftweb.json.JValue

/**
 *  Represented as a Topic in PlurkTop.
 *
 *  @param  name            The english name of this collection.
 *  @param  language        The language set of this collection.
 *  @param  translatedName  The name of this collection in local language.
 */
case class Collection(name: String, languages: List[String], translatedName: String)

object Collection {

  /**
   *  Create Collection object from JSON dta.
   *
   *  @param  jsonData  JSON returned by Plurk.
   *  @return           Collection object corresponding to jsonData.
   */
  def apply(jsonData: JValue): Collection = {
    
    Collection(
      name = jsonData(0).values.toString,
      languages = jsonData(1).values.toString.split(",").toList,
      translatedName = jsonData(2).values.toString
    )

  }

}

