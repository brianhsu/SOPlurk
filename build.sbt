name := "SOPlurk"

organization := "org.bone"

version := "0.1"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

scalacOptions in (Compile, doc) ++= Seq(
  "-doc-root-content", "src/doc/index.txt",
  "-doc-title", "SOPlurk API document"
)

scalaVersion := "2.10.0"

libraryDependencies ++= Seq(
    "org.scribe" % "scribe" % "1.3.2",
    "org.scalatest" %% "scalatest" % "1.9.1" % "test",
    "net.liftweb" %% "lift-json" % "2.5-M4"
)

publishTo := Some(
  Resolver.sftp("bone", "bone.twbbs.org.tw", "public_html/ivy")
)
