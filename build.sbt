name := "SOPlurk"

organization := "org.bone"

version := "0.2"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

scalacOptions in (Compile, doc) ++= Seq(
  "-doc-root-content", "README.scaladoc",
  "-doc-title", "SOPlurk 0.2 API document"
)

target in Compile in doc <<= (baseDirectory / "api")

scalaVersion := "2.10.0"

resolvers +=
  "scribe-java-mvn-repo" at "https://raw.github.com/fernandezpablo85/scribe-java/mvn-repo/"

libraryDependencies ++= Seq(
  "org.scribe" % "scribe" % "1.3.6",
  "org.scalatest" %% "scalatest" % "1.9.1" % "test",
  "net.liftweb" %% "lift-json" % "2.5-M4",
  "commons-httpclient" % "commons-httpclient" % "3.1",
  "com.typesafe.akka" %% "akka-actor" % "2.1.0"
)

publishTo := Some(
  Resolver.sftp("bone", "bone.twbbs.org.tw", "public_html/ivy")
)
