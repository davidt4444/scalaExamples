# postconsumerkafka setup
To look for commonly used dependencies
https://github.com/confluentinc/examples

sbt new scala/scala3.g8
Name: PostConsumerKafka

https://x.com/i/grok/share/cWcTyhVhkDLzWg7EXsAjgTsV6
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

# local kafka
https://anishmahapatra.medium.com/apache-kafka-102-how-to-set-up-kafka-on-your-local-68f432dfb1ab
https://kafka.apache.org/downloads
unzip to home 
terminal 1
cd ~/kafka_2.13-3.9.0
bin/zookeeper-server-start.sh config/zookeeper.properties
terminal 2
cd ~/kafka_2.13-3.9.0
bin/kafka-server-start.sh config/server.properties
terminal 3
cd ~/kafka_2.13-3.9.0
bin/connect-standalone.sh config/connect-standalone.properties
terminal 4
In project dir run
sbt run
bin/kafka-topics.sh --bootstrap-server localhost:9092 --list

# producer
bin/kafka-console-producer.sh --topic post-topic --bootstrap-server localhost:9092
# consumer
bin/kafka-console-consumer.sh --topic post-topic --bootstrap-server localhost:9092

If you run the consumer before running the project, you should see a json snippet for each post.
