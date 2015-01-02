name := "SOPlurk"

organization := "org.bone"

version := "0.3.2"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

scalacOptions in (Compile, doc) ++= Seq(
  "-doc-root-content", "README.scaladoc",
  "-doc-title", "SOPlurk 0.2 API document"
)

target in Compile in doc <<= (baseDirectory / "api")

scalaVersion := "2.11.4"

resolvers +=
  "scribe-java-mvn-repo" at "https://raw.github.com/fernandezpablo85/scribe-java/mvn-repo/"

libraryDependencies ++= Seq(
  "org.scribe" % "scribe" % "1.3.6",
  "net.liftweb" %% "lift-json" % "2.6-RC2",
  "com.typesafe.akka" %% "akka-actor" % "2.3.8"
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.1" % "test"
)

publishTo := Some(
  Resolver.sftp("bone", "bone.twbbs.org.tw", "site/ROOT/ivy/")
)
