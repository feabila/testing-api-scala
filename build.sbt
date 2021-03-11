name := "testing-api-scala"

version := "0.1"

scalaVersion := "2.13.5"

idePackagePrefix := Some("com.study")

libraryDependencies ++= Seq(
  "io.spray" % "spray-can" % "1.1-M8",
  "io.spray" % "spray-http" % "1.1-M8",
  "io.spray" % "spray-routing" % "1.1-M8",
  "net.liftweb" %% "lift-json" % "2.5.1",
  "com.typesafe.slick" %% "slick" % "1.0.1",
  "mysql" % "mysql-connector-java" % "5.1.25",
  "com.typesafe.akka" %% "akka-actor" % "2.1.4",
  "com.typesafe.akka" %% "akka-slf4j" % "2.1.4",
  "ch.qos.logback" % "logback-classic" % "1.0.13"
)

resolvers ++= Seq(
  ("Spray repository" at "http://repo.spray.io").withAllowInsecureProtocol(true),
  ("Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/").withAllowInsecureProtocol(true),
  "typesafe" at "https://repo.typesafe.com/typesafe/ivy-releases/"
)