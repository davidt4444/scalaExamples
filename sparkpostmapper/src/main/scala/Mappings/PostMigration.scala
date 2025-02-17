package com.ads.sparkpostmapper.mappings

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._

class PostMigration {

  def pyPostToCPPPost(): Unit = {
    //val spark = SparkSession.builder()
    //  .appName("PostMigration")
    //  .config("spark.sql.warehouse.dir", "/user/hive/warehouse")
    //  .enableHiveSupport()
    //  .getOrCreate()
    val spark = SparkSession.builder()
    .master("local[1]")
    .appName("PostMigration")
    .getOrCreate();

    // Define source schema
    val sourceSchema = StructType(Array(
      StructField("id", IntegerType, nullable = false),
      StructField("uniqueId", StringType, nullable = false),
      StructField("title", StringType, nullable = true),
      StructField("author", StringType, nullable = true),
      StructField("date", TimestampType, nullable = true),
      StructField("content", BinaryType, nullable = true)
    ))

    // Read from the source table
    val postsDF = spark.read
      .format("jdbc")
      .option("url", scala.io.Source.fromFile("../../aws-resources/localhost-mac.txt").mkString)
      //.option("url", "jdbc:mysql://your-mysql-host:3306/your-database")
      .option("driver", "com.mysql.cj.jdbc.Driver")
      .option("dbtable", "Post")
      //.option("user", "your-username")
      //.option("password", "your-password")
      .load()
      .select(
        col("id"),
        col("uniqueId"),
        col("title"),
        col("author"),
        col("date").as("createdAt"),
        col("content").cast(StringType)
      )

    // Transform data to match the destination schema
    val cppPostsDF = postsDF
      .withColumn("category", lit(null).cast(StringType))
      .withColumn("updatedAt", lit(null).cast(TimestampType))
      .withColumn("likesCount", lit(0).cast(IntegerType))
      .withColumn("authorId", lit(null).cast(IntegerType))
      .withColumn("isPublished", lit(true).cast(BooleanType))
      .withColumn("views", lit(0).cast(IntegerType))
      .filter(length(col("title")) >= 5 && length(col("title")) <= 200) // CHECK constraint for title
      .filter(length(col("content")) <= 10000) // CHECK constraint for content

    // Write to the destination table
    cppPostsDF.write
      .format("jdbc")
      .option("url", scala.io.Source.fromFile("../../aws-resources/localhost-mac-java.txt").mkString)
      //.option("url", "jdbc:mysql://your-mysql-host:3306/your-destination-database")
      .option("driver", "com.mysql.cj.jdbc.Driver")
      .option("dbtable", "JPost")
      //.option("user", "your-username")
      //.option("password", "your-password")
      .mode("append")
      .save()

    spark.stop()
  }
}