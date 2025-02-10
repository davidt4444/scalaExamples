package com.ads.flinkpostmapper.sinks

import com.ads.flinkpostmapper.models.Post
import com.ads.flinkpostmapper.FlinkConstants
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.functions.sink.SinkFunction
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction
import org.apache.flink.configuration.Configuration
import java.sql.{Connection, DriverManager, PreparedStatement}

class JDBCPostSink extends RichSinkFunction[Post] {
    private var connection: Connection = _
    private var preparedStatement: PreparedStatement = _

    override def open(parameters: Configuration): Unit = {
        // Register the MySQL JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver")
        //connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/yourdatabase", "username", "password")
        connection = DriverManager.getConnection(scala.io.Source.fromFile(FlinkConstants.project_sub_dir.toString+"/aws-resources/localhost-mac-scala.txt").mkString)
        // Prepare the SQL statement
        preparedStatement = connection.prepareStatement(
        "INSERT INTO Post (id, title, content, createdAt, author, category, updatedAt, likesCount, authorId, isPublished, views) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE " +
        "title = VALUES(title), content = VALUES(content), updatedAt = VALUES(updatedAt), likesCount = VALUES(likesCount), " +
        "author = VALUES(author), category = VALUES(category), isPublished = VALUES(isPublished), views = VALUES(views)"
        )
    }

    override def close(): Unit = {
        if (preparedStatement != null) {
        preparedStatement.close()
        }
        if (connection != null) {
        connection.close()
        }
    }

    override def invoke(post: Post, context: SinkFunction.Context): Unit = {
        preparedStatement.setObject(1, post.id.getOrElse(null)) // Assuming id can be null for insert, otherwise update
        preparedStatement.setString(2, post.title)
        preparedStatement.setString(3, post.content)
        preparedStatement.setTimestamp(4, post.createdAt)
        preparedStatement.setString(5, post.author.orNull)
        preparedStatement.setString(6, post.category.orNull)
        preparedStatement.setTimestamp(7, post.updatedAt.orNull)
        preparedStatement.setInt(8, post.likesCount)
        preparedStatement.setObject(9, post.authorId.getOrElse(null))
        preparedStatement.setBoolean(10, post.isPublished)
        preparedStatement.setInt(11, post.views)

        preparedStatement.executeUpdate()
    }
}
