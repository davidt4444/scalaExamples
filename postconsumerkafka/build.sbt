val scala3Version = "3.6.3"

lazy val root = project
  .in(file("."))
  .settings(
    name := "PostConsumerKafka",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies += "org.scalameta" %% "munit" % "1.0.0" % Test
  )
//    Opts.resolver.mavenLocalFile,
// resolvers += Resolver.url("my-test-repo", url("https://mvnrepository.com/artifact/"))
  resolvers ++= Seq (
    Resolver.mavenLocal,
    "Confluent" at "https://packages.confluent.io/maven",
    "my-test-repo" at "https://mvnrepository.com/artifact/"
  )
  libraryDependencies ++= Seq(
    "org.slf4j" % "slf4j-api" % "2.0.9",
    "org.slf4j" % "slf4j-simple" % "2.0.9"
  )
  libraryDependencies ++= Seq(
    "org.apache.kafka" % "kafka-clients" % "3.0.0",
    "io.confluent" % "kafka-json-serializer" % "7.8.0"
  )
