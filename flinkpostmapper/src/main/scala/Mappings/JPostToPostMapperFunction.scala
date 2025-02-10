package com.ads.flinkpostmapper.mappings

import com.ads.flinkpostmapper.models._

import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.configuration.Configuration
import java.sql.Timestamp

  // Custom Map Function to convert JPost to Post
  class JPostToPostMapperFunction extends RichMapFunction[JPost, Post] {
    override def open(parameters: Configuration): Unit = {
      // Initialization code if needed
    }

    override def map(value: JPost): Post = {
      Post(
        id = value.id,
        title = value.title,
        content = value.content,
        createdAt = Timestamp.valueOf(value.createdAt),
        author = value.author,
        category = value.category,
        updatedAt = value.updatedAt.map(Timestamp.valueOf),
        likesCount = value.likesCount,
        authorId = value.authorId,
        isPublished = value.isPublished,
        views = value.views
      )
    }
  }
