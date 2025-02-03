To look for commonly used dependencies
https://github.com/confluentinc/examples

sbt new scala/scala3.g8
Name: PostProcessKafka

https://x.com/i/grok/share/epcaVMC8P95tNVyl2OgZtZ8b4
https://x.com/i/grok?conversation=1886195281915470194

Some small changes in addition to the code from the generator

https://stackoverflow.com/questions/7421612/slf4j-failed-to-load-class-org-slf4j-impl-staticloggerbinder
  libraryDependencies ++= Seq(
    "org.slf4j" % "slf4j-api" % "2.0.9",
    "org.slf4j" % "slf4j-simple" % "2.0.9"
  )

To solve the problem with the serializer
  libraryDependencies ++= Seq(
    "io.confluent" % "kafka-json-serializer" % "7.8.0"
  )

Finally,
sbt run


