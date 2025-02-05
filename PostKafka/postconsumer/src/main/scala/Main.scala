import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import scala.collection.JavaConverters._
//These three are needed to deserialize
import com.ads.postconsumer.models.Post
import play.api.libs.json._
import com.ads.postconsumer.controllers.PostJsonSupport._ // Assuming you have this defined as before

object KafkaConsumerExample {

  def main(args: Array[String]): Unit = {
    // Kafka broker list
    val bootstrapServers = "localhost:9092"
    val topicName = "your-topic-name"

    // Properties for the consumer
    val props = new java.util.Properties()
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer])
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer])
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer-group-id") // Change this to your consumer group ID
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest") // Start reading from the beginning if there's no initial offset in Kafka

    // Create the consumer
    val consumer = new KafkaConsumer[String, String](props)

    try {
      // Subscribe to the topic
      consumer.subscribe(java.util.Collections.singletonList(topicName))

      // Poll for new data
      while (true) {
        val records = consumer.poll(java.time.Duration.ofMillis(100))
        for (record <- records.asScala) {

          println(s"Received message: ${record.value()} from partition ${record.partition()} at offset ${record.offset()}")
          val payload = record.value().toString
          val post1 = Json.parse(payload).as[Post]
          println("################")
          println("-----------"+post1)
          println("################")

        }
      }
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      // Ensure to close the consumer to release resources
      consumer.close()
    }
  }
}
