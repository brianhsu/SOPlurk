package org.bone.soplurk

import org.bone.soplurk.api.PlurkAPI

package object bot {
  type BotAction = PlurkAPI => PartialFunction[Any, Unit]
} 
