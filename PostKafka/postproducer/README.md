## sbt project compiled with Scala 3

### Usage

sbt new scala/scala3.g8
Name: PostProducer

for the kafka client/producer
https://x.com/i/grok?conversation=1886834165015806204
for the service call
https://x.com/i/grok/share/eJt3GHhGJ0eDwqehXa9WNH426

Some small changes in addition to the code from the generator

  libraryDependencies += "org.apache.kafka" % "kafka-clients" % "3.0.0"
https://stackoverflow.com/questions/7421612/slf4j-failed-to-load-class-org-slf4j-impl-staticloggerbinder
  libraryDependencies ++= Seq(
    "org.slf4j" % "slf4j-api" % "2.0.9",
    "org.slf4j" % "slf4j-simple" % "2.0.9"
  )

Also add in all of the requirements for the calls to the post service by adding in the PostJsonSupport, Post, PostRepository and PostService objects from bcs and make the necessary changes to the project path and to the path to the connection string in the repository. Next, follow the logic from 
https://x.com/i/grok/share/en90qCTHi5G8t2BWGSGoWNOMQ
to call the service to get the post data to serialize as Json and stream.

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
# list topics
cd ~/kafka_2.13-3.9.0
bin/kafka-topics.sh --bootstrap-server localhost:9092 --list

# clear data
cd ~/kafka_2.13-3.9.0
bin/kafka-topics.sh --topic post-topic --bootstrap-server localhost:9092 --delete 
bin/kafka-delete-records.sh command

# producer
bin/kafka-console-producer.sh --topic post-topic --bootstrap-server localhost:9092
# consumer
bin/kafka-console-consumer.sh --topic post-topic --bootstrap-server localhost:9092

If you run the consumer before running the project, you should see a json snippet for each post.
