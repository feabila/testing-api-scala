name := "testing-api-scala"

version := "0.1"

ThisBuild / scalaVersion := "2.11.7"
ThisBuild / organization := "com.study"

idePackagePrefix := Some("com.study")

val AkkaVersion = "2.5.32"
val IOSprayVersion = "1.3.4"
val NetLiftWebVersion = "3.4.3"
val SlickVersion = "3.3.3"
val MySQLVersion = "8.0.23"
val LogBackVersion= "1.2.3"

resolvers ++= Seq(
  ("spray repo" at "http://repo.spray.io").withAllowInsecureProtocol(true),
  Resolver.typesafeRepo("releases")
)

libraryDependencies ++= Seq(
  "io.spray" %% "spray-can" % IOSprayVersion,
  "io.spray" %% "spray-http" % IOSprayVersion,
  "io.spray" %% "spray-routing" % IOSprayVersion,
  "net.liftweb" %% "lift-json" % NetLiftWebVersion,
  "com.typesafe.slick" %% "slick" % SlickVersion,
  "mysql" % "mysql-connector-java" % MySQLVersion,
  "com.typesafe.akka" %% "akka-actor" % AkkaVersion,
  "com.typesafe.akka" %% "akka-slf4j" % AkkaVersion,
  "ch.qos.logback" % "logback-classic" % LogBackVersion
)