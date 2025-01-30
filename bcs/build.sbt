name := """basic-content-service"""
organization := "com.ads"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.16"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test
//"com.typesafe.slick" %% "slick" % "3.3.3", >>   "org.playframework" %% "play-slick" % "6.1.1",
//"com.typesafe.slick" %% "slick-hikaricp" % "3.3.3", >>   "com.typesafe.slick" %% "slick-hikaricp" % "3.5.1",
//"mysql" % "mysql-connector-java" % "8.0.28", >>   "mysql" % "mysql-connector-java" % "8.0.33"
//"com.typesafe.akka" %% "akka-http" % "10.2.7", >> "com.typesafe.akka" %% "akka-http" % "10.5.3",
//"com.typesafe.akka" %% "akka-stream" % "2.6.18", >> "com.typesafe.akka" %% "akka-stream" % "2.8.8",
//"com.typesafe.play" %% "play-json" % "2.9.2" >> "org.playframework" %% "play-json" % "3.1.0-M1",

libraryDependencies ++= Seq(
  "org.playframework" %% "play-slick" % "6.1.1",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.5.1",
  "mysql" % "mysql-connector-java" % "8.0.33",
  "com.typesafe.akka" %% "akka-http" % "10.5.3",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.5.3",
  "com.typesafe.akka" %% "akka-stream" % "2.8.8",
  "org.playframework" %% "play-json" % "3.1.0-M1"
)
libraryDependencies ++= Seq(
)
libraryDependencies += guice

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.ads.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.ads.binders._"
