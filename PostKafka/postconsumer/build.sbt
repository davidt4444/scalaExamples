val scala3Version = "3.6.3"

lazy val root = project
  .in(file("."))
  .settings(
    name := "PostConsumer",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies += "org.scalameta" %% "munit" % "1.0.0" % Test
  )
  libraryDependencies += "org.apache.kafka" % "kafka-clients" % "3.0.0"
  libraryDependencies ++= Seq(
    "org.slf4j" % "slf4j-api" % "2.0.9",
    "org.slf4j" % "slf4j-simple" % "2.0.9"
  )

libraryDependencies ++= Seq(
  "org.playframework" %% "play-slick" % "6.1.1",
  "org.playframework" %% "play-json" % "3.1.0-M1",
)
  
