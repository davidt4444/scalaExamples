val scala3Version = "3.6.3"

lazy val root = project
  .in(file("."))
  .settings(
    name := "PostProducer",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies += "org.scalameta" %% "munit" % "1.0.0" % Test
  )
  libraryDependencies += "org.apache.kafka" % "kafka-clients" % "3.0.0"
  libraryDependencies ++= Seq(
    "org.slf4j" % "slf4j-api" % "2.0.9",
    "org.slf4j" % "slf4j-simple" % "2.0.9"
  )
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
  "org.playframework" %% "play-json" % "3.1.0-M1",
  "org.playframework" %% "play-guice" % "3.1.0-M1"
)
