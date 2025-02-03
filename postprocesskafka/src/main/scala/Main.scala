import java.sql.{Connection, DriverManager, ResultSet}
import java.time.Instant
import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

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

object MySQLToKafka {
  def main(args: Array[String]): Unit = {
    // MySQL connection details
    //val url = "jdbc:mysql://localhost:3306/your_database"
    //val user = "your_username"
    //val password = "your_password"

    // Kafka producer configuration
    val kafkaProps = new Properties()
    kafkaProps.put("bootstrap.servers", "localhost:9092")
    kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    kafkaProps.put("value.serializer", "io.confluent.kafka.serializers.KafkaJsonSerializer")
    kafkaProps.put("acks", "all")

    val producer = new KafkaProducer[String, Post](kafkaProps)

    try {
      // Connect to MySQL
      //val connection = DriverManager.getConnection(url, user, password)
      val connection = DriverManager.getConnection(scala.io.Source.fromFile("../../aws-resources/localhost-mac-scala.txt").mkString)
      val statement = connection.createStatement()
      val resultSet: ResultSet = statement.executeQuery("SELECT * FROM post")

      while (resultSet.next()) {
        // Extract data from ResultSet to Post case class
        val post = Post(
          id = Option(resultSet.getInt("id")),
          title = resultSet.getString("title"),
          content = resultSet.getString("content"),
          createdAt = Instant.ofEpochMilli(resultSet.getTimestamp("createdAt").getTime),
          author = Option(resultSet.getString("author")),
          category = Option(resultSet.getString("category")),
          updatedAt = Option(resultSet.getTimestamp("updatedAt")).map(_.toInstant),
          likesCount = resultSet.getInt("likesCount"),
          authorId = Option(resultSet.getInt("authorId")),
          isPublished = resultSet.getBoolean("isPublished"),
          views = resultSet.getInt("views")
        )

        // Send the post to Kafka topic
        val record = new ProducerRecord[String, Post]("post-topic", post.id.map(_.toString).getOrElse(""), post)
        producer.send(record)
        println(s"Post with ID ${post.id.getOrElse("No ID")} sent to Kafka")
      }

      resultSet.close()
      statement.close()
      connection.close()
    } catch {
      case e: Exception => println(s"An error occurred: ${e.getMessage}")
    } finally {
      producer.close()
    }
  }
}
