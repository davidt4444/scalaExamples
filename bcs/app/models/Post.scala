// /postproducer/src/models/Post.scala
package com.ads.bcs.app.models

import java.time.Instant;
import java.sql.Timestamp;

case class Post(
                 id: Option[Int] = None,
                 uniqueId: Option[String]=None,
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

object Post {

  def mapperTo(
                id: Option[Int],
                uniqueId: Option[String],
                title: String,
                content: String,
                createdAt: Timestamp,
                author: Option[String],
                category: Option[String],
                updatedAt: Option[Timestamp],
                likesCount: Int,
                authorId: Option[Int],
                isPublished: Boolean,
                views: Int
              ) = apply(id,
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
