package org.bone.soplurk.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateTimeUtils {

  /**
   *  Convert Plurk date / time string to Java's Date object.
   *
   *  @param    dateString  The date / time string in Plurk JSON.
   *  @return               The Java's Date object corresponding to dateString
   */
  def fromPlurkDate(dateString: String): Date = {
    val dateFormatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US)
    dateFormatter.parse(dateString)
  }

}
