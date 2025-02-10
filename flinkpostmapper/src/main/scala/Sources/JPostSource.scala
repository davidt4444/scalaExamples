package com.ads.flinkpostmapper.sources

import com.ads.flinkpostmapper.FlinkConstants
import com.ads.flinkpostmapper.models.JPost

import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction
import org.apache.flink.streaming.api.functions.source.SourceFunction
import java.sql.{Connection, DriverManager, SQLException}


// Custom SourceFunction for MySQL
class JPostSource extends RichParallelSourceFunction[JPost] {
    private var running = true

    override def open(parameters: Configuration): Unit = {
        // Register JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver")
    }

    override def run(sourceContext: SourceFunction.SourceContext[JPost]): Unit = {
        var connection: Connection = null
        try {
        //connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/yourdatabase", "username", "password")
        connection = DriverManager.getConnection(scala.io.Source.fromFile(FlinkConstants.project_sub_dir.toString+"/aws-resources/localhost-mac-java.txt").mkString)
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM JPost")

        while (resultSet.next() && running) {
            val jPost = JPost(
            id = Option(resultSet.getInt("id")),
            title = resultSet.getString("title"),
            content = resultSet.getString("content"),
            createdAt = resultSet.getTimestamp("createdAt").toLocalDateTime,
            author = Option(resultSet.getString("author")),
            category = Option(resultSet.getString("category")),
            updatedAt = Option(resultSet.getTimestamp("updatedAt")).map(_.toLocalDateTime),
            likesCount = resultSet.getInt("likesCount"),
            authorId = Option(resultSet.getInt("authorId")),
            isPublished = resultSet.getBoolean("isPublished"),
            views = resultSet.getInt("views")
            )
            sourceContext.collect(jPost)
        }
        } catch {
        case e: SQLException => e.printStackTrace()
        } finally {
        if (connection != null) {
            try {
            connection.close()
            } catch {
            case e: SQLException => e.printStackTrace()
            }
        }
        }
    }

    override def cancel(): Unit = {
        running = false
    }
}


