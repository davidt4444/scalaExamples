import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer
import java.util.Properties

import com.ads.postproducer.service.PostService
import com.ads.postproducer.repository.PostRepository
import slick.jdbc.MySQLProfile.api._
import scala.concurrent.{ExecutionContext, Future, Await}
import scala.concurrent.duration.Duration
import java.time.Instant;
//These three are needed to deserialize
import com.ads.postproducer.models.Post
import play.api.libs.json._
import com.ads.postproducer.controllers.PostJsonSupport._ // Assuming you have this defined as before

object Main extends App {
  implicit val ec: ExecutionContext = ExecutionContext.global


  // Assuming you have a way to initialize your database, like:
  // val db = Database.forConfig("mysqlDB") // Configuration would be in application.conf or similar

  // Directly instantiate repository and service
  val postRepository = new PostRepository()
  val postService = new PostService(postRepository)

  val futurePost = postService.findAllPosts

  // Await the result (not recommended for production, just for demonstration)
  val post_list = Await.result(futurePost, Duration.Inf)
  
  post_list.foreach(post=>{
    println(s"Created post: $post")
  })

  // Clean up
  //Await.result(db.close(), Duration.Inf)
  
  // Kafka broker list
  val bootstrapServers = "localhost:9092"
  val topicName = "your-topic-name"

  // Properties for the producer
  val props = new Properties()
  props.put("bootstrap.servers", bootstrapServers)
  props.put("key.serializer", classOf[StringSerializer].getName)
  props.put("value.serializer", classOf[StringSerializer].getName)

  // Create the producer
  val producer = new KafkaProducer[String, String](props)

  try {
    // Example of sending messages
    //val messages = List(
    //  "Hello, Kafka!", 
    //  "This is a test message.", 
    //  "Another message for the topic."
    //)
    val messages = post_list

    messages.foreach { message =>
      var payload = Json.toJson(message).toString
      val record = new ProducerRecord[String, String](topicName, payload)

      //val post1 = Json.parse(payload).as[Post]
      //println("-----------"+post1)


      producer.send(record)
      println(s"Sent message: $message")
    }

    // Ensure all messages are sent before proceeding
    producer.flush()

  } catch {
    case e: Exception => e.printStackTrace()
  } finally {
    // Ensure to close the producer to release resources
    producer.close()
  }
}

