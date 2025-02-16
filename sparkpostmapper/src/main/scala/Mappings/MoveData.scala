package com.ads.sparkpostmapper.mappings

import org.apache.spark.sql.SparkSession
import java.sql.Timestamp

class MoveData {

  def movement(): Unit = {
    // Initialize Spark session
    //val spark = SparkSession.builder()
    //  .appName("MovePostDataBetweenDBs")
    //  .config("spark.master", "local")
    //  .getOrCreate()
    val spark = SparkSession.builder()
    .master("local[1]")
    .appName("BlogPostMapper")
    .getOrCreate();

    import spark.implicits._

    try {
      // JDBC URLs for source and destination databases
      //val sourceJdbcUrl = "jdbc:mysql://source_host:3306/source_database"
      //val destJdbcUrl = "jdbc:mysql://dest_host:3306/destination_database"

      // Connection properties for source database
      //val sourceProperties = new java.util.Properties()
      //sourceProperties.put("user", "source_username")
      //sourceProperties.put("password", "source_password")

      // Connection properties for destination database
      //val destProperties = new java.util.Properties()
      //destProperties.put("user", "dest_username")
      //destProperties.put("password", "dest_password")

      // Read data from Post table in source database
    //  val postsDF = spark.read.jdbc(sourceJdbcUrl, "Post", sourceProperties)
    // Read data from MySQL 'post' table
    val postDF = spark.read
    .format("jdbc")
    .option("url", scala.io.Source.fromFile("../../aws-resources/thenameofyourbrand.txt").mkString)
    .option("driver", "com.mysql.cj.jdbc.Driver")
    .option("dbtable", "Post")
    .load()

      // Write data to Post_stage table in destination database
     // postsDF.write.mode("append").jdbc(destJdbcUrl, "Post_stage", destProperties)
    // Write transformed data to MySQL 'JPost' table
    postDF.write
      .format("jdbc")
      .option("url", scala.io.Source.fromFile("../../aws-resources/localhost-mac.txt").mkString)
      .option("driver", "com.mysql.cj.jdbc.Driver")
      .option("dbtable", "Post_stage")
      .mode("append")
      .save()

      println("Data has been moved from source Post to destination Post_stage successfully.")

    } catch {
      case e: Exception => 
        println(s"An error occurred: ${e.getMessage}")
    } finally {
      // Stop spark session
      spark.stop()
    }
  }
}