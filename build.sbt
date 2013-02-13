name := "SPlurk2"

version := "0.1"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

scalaVersion := "2.10.0"

libraryDependencies ++= Seq(
    "org.scribe" % "scribe" % "1.3.2",
    "org.scalatest" %% "scalatest" % "1.9.1" % "test",
    "net.liftweb" %% "lift-json" % "2.5-M4"
)
