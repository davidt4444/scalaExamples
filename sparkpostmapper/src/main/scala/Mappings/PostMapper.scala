package com.ads.sparkpostmapper.mappings

import com.ads.sparkpostmapper.models.Post
import com.ads.sparkpostmapper.models.JPost

import org.apache.spark.sql.{SparkSession, DataFrame}
import org.apache.spark.sql.types._
import java.sql.Timestamp
import java.time.LocalDateTime
import org.apache.spark.sql.{Encoder, Row}

class PostMapper (){
  def postToJpost(): Unit = {
    // Create Spark session
    //val spark = SparkSession.builder()
    //  .appName("PostToBlogPostMapper")
    //  .config("spark.driver.allowMultipleContexts", "true")
    //  .getOrCreate()
    val spark = SparkSession.builder()
    .master("local[1]")
    .appName("BlogPostMapper")
    .getOrCreate();

    import spark.implicits._

    // Define schema for Post table
    val postSchema = StructType(Seq(
      StructField("id", IntegerType, nullable = true),
      StructField("uniqueId", StringType, nullable = false),
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

    // Read data from MySQL 'post' table
    val postDF = spark.read
      .format("jdbc")
      .option("url", scala.io.Source.fromFile("../../aws-resources/localhost-mac-scala.txt").mkString)
      .option("driver", "com.mysql.cj.jdbc.Driver")
      .option("dbtable", "post")
      //.option("user", "<username>")
      //.option("password", "<password>")
      .load()

    // Map DataFrame to BlogPost (Java class representation in Scala)
    val blogPosts = postDF.map { row =>
      // If rebuilding 
      // val id = Integer.valueOf(row.getAs[Long]("id").toString) // Convert Long to Int for id
      //else add new data without old id
      val id = null
      val createdAt = row.getAs[Timestamp]("createdAt").toLocalDateTime
      // Wrapping nullable fields in Option
      val author = Option(row.getAs[String]("author"))
      val category = Option(row.getAs[String]("category"))
      val updatedAt = 
        Option(row.getAs[Timestamp]("updatedAt")).map(_.toLocalDateTime) // Wrap in Option
      val authorId = Option(row.getAs[Int]("authorId"))

      new JPost(
        id,
        row.getAs[String]("uniqueId"),
        row.getAs[String]("title"),
        row.getAs[String]("content"),
        createdAt,
        author,
        category,
        updatedAt,
        row.getAs[Int]("likesCount"),
        authorId,
        row.getAs[Boolean]("isPublished"),
        row.getAs[Int]("views")
      )
    }

    // Convert BlogPost back to DataFrame for writing to database
    val blogPostDF = blogPosts.toDF()

    // Write transformed data to MySQL 'JPost' table
    blogPostDF.write
      .format("jdbc")
      .option("url", scala.io.Source.fromFile("../../aws-resources/localhost-mac-java.txt").mkString)
      .option("driver", "com.mysql.cj.jdbc.Driver")
      .option("dbtable", "JPost") // Assuming this is your target table name
      //.option("user", "<username>")
      //.option("password", "<password>")
      .mode("append")
      .save()

    println("Data has been written to the 'JPost' table.")

    spark.stop()
  }
  def jpostToPost(): Unit = {
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

    val postsDF = jdbcDF.map { row =>
      
      // If rebuilding 
      // val id = Option(row.getAs[Long]("id")) // Convert Long to Int for id
      //else add new data without old id
      val id = null
      val uniqueId = row.getAs[String]("uniqueId")
      val title = row.getAs[String]("title")
      val content = row.getAs[String]("content")
      val createdAt = row.getAs[Timestamp]("createdAt")
      val author = Option(row.getAs[String]("author"))
      val category = Option(row.getAs[String]("category"))
      val updatedAt = Option(row.getAs[Timestamp]("updatedAt"))
      val likesCount = row.getAs[Int]("likesCount")
      val authorId = Option(row.getAs[Int]("authorId"))
      val isPublished = row.getAs[Boolean]("isPublished")
      val views = row.getAs[Int]("views")
      new Post(
        id,
        uniqueId,
        title,
        content,
        createdAt,
        author,
        category,
        updatedAt,
        likesCount,
        authorId,
        isPublished,
        views
      )
    }
    // You can also map to case class Post
    // in the data frame like the example below
    /*
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
    */
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

}
