import org.apache.spark.sql.{SparkSession, DataFrame}
import org.apache.spark.sql.types._
import java.sql.Timestamp
import java.time.LocalDateTime
import org.apache.spark.sql.{Encoder, Row}

object BlogPostMapper {
  def main(args: Array[String]): Unit = {
    // Create Spark session
    //val spark = SparkSession.builder()
    //  .appName("BlogPostMapper")
    //  .config("spark.driver.allowMultipleContexts", "true")
    //  .getOrCreate()
    val spark = SparkSession.builder()
    .master("local[1]")
    .appName("BlogPostMapper")
    .getOrCreate();

    import spark.implicits._

    // Define the schema for the MySQL table based on BlogPost
    val blogPostSchema = StructType(Seq(
      StructField("id", LongType, nullable = true),
      StructField("title", StringType, nullable = false),
      StructField("content", StringType, nullable = false),
      StructField("createdAt", TimestampType, nullable = false),
      StructField("author", StringType, nullable = true),
      StructField("category", StringType, nullable = true),
      StructField("updatedAt", TimestampType, nullable = true),
      StructField("likesCount", IntegerType, nullable = false),
      StructField("authorId", IntegerType, nullable = true),
      StructField("isPublished", BooleanType, nullable = false),
      StructField("views", IntegerType, nullable = false)
    ))

    // Read data from MySQL source table
    val jdbcDF = spark.read
      .format("jdbc")
      .option("url", scala.io.Source.fromFile("../../aws-resources/localhost-mac-java.txt").mkString)
      .option("driver", "com.mysql.cj.jdbc.Driver")
      .option("dbtable", "JPost")
      //.option("user", "")
      //.option("password", "")
      .load()

    // Map to case class Post
    val postsDF = jdbcDF.select(
      $"id".cast(LongType).as[Option[Long]],
      $"title".as[String],
      $"content".as[String],
      $"createdAt".as[Timestamp],
      $"author".as[Option[String]],
      $"category".as[Option[String]],
      $"updatedAt".as[Option[Timestamp]],
      $"likesCount".as[Int],
      $"authorId".as[Option[Int]],
      $"isPublished".as[Boolean],
      $"views".as[Int]
    ).as[Post]

    // Write the transformed data to MySQL "post" table
    postsDF.write
      .format("jdbc")
      .option("url", scala.io.Source.fromFile("../../aws-resources/localhost-mac-scala.txt").mkString)
      .option("driver", "com.mysql.cj.jdbc.Driver")
      .option("dbtable", "post") // Here we specify the destination table name
      //.option("user", "")
      //.option("password", "")
      .mode("append") // Use 'append' if you don't want to overwrite existing data, 'overwrite' to replace the table
      .save()

    println("Data has been written to the 'post' table.")

    spark.stop()
  }

  case class Post(
    id: Option[Long] = None,
    title: String,
    content: String,
    createdAt: Timestamp,
    author: Option[String] = None,
    category: Option[String] = None,
    updatedAt: Option[Timestamp] = None,
    likesCount: Int = 0,
    authorId: Option[Int] = None,
    isPublished: Boolean,
    views: Int = 0
  )
}