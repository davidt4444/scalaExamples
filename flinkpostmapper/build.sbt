val scala3Version = "2.12.20"

lazy val root = project
  .in(file("."))
  .settings(
    name := "FlinkPostMapper",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies += "org.scalameta" %% "munit" % "1.0.0" % Test
  )
  libraryDependencies ++= Seq(
    "org.slf4j" % "slf4j-api" % "2.0.9",
    "org.slf4j" % "slf4j-simple" % "2.0.9"
  )
libraryDependencies ++= Seq(
  "org.apache.flink" %% "flink-scala" % "1.20.0", // Use the latest version available
  "org.apache.flink" %% "flink-streaming-scala" % "1.20.0",
  "mysql" % "mysql-connector-java" % "8.0.33",
)
