import java.time.Duration
import java.util.Properties
import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}
import java.time.Instant

// Define the Post case class
case class Post(
                 id: Option[Int] = None,
                 title: String,
                 content: String,
                 createdAt: Instant,
                 author: Option[String] = None,
                 category: Option[String] = None,
                 updatedAt: Option[Instant] = None,
                 likesCount: Int = 0,
                 authorId: Option[Int] = None,
                 isPublished: Boolean,
                 views: Int = 0
               )

object PostConsumer {
  def main(args: Array[String]): Unit = {
    // Kafka consumer configuration
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "io.confluent.kafka.serializers.KafkaJsonDeserializer")
    props.put("group.id", "post-group")
    props.put("auto.offset.reset", "earliest")
    props.put("enable.auto.commit", "true")

    // Create the consumer
    val consumer = new KafkaConsumer[String, Post](props)

    try {
      // Subscribe to the topic
      consumer.subscribe(java.util.Collections.singletonList("post-topic"))

      // Poll for new data
      while (true) {
        val records: ConsumerRecords[String, Post] = consumer.poll(Duration.ofMillis(100))
        records.forEach (record => {
          val post = record.value()
          println(s"Received post with ID: ${post.id.getOrElse("No ID")}")
          println(s"Title: ${post.title}")
          println(s"Created At: ${post.createdAt}")
          println(s"Content: ${post.content}")
          println(s"Is Published: ${post.isPublished}")
          println("-------------------")
        }
        )
      }
    } catch {
      case e: Exception => println(s"An error occurred: ${e.getMessage}")
    } finally {
      consumer.close()
    }
  }
}
