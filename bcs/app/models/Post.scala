// /bcs/app/models/Post.scala
package com.ads.bcs.app.models

import java.time.LocalDateTime;

case class Post(
                 id: Option[Int] = None,
                 title: String,
                 content: String,
                 createdAt: LocalDateTime,
                 author: Option[String] = None,
                 category: Option[String] = None,
                 updatedAt: Option[LocalDateTime] = None,
                 likesCount: Int = 0,
                 authorId: Option[Int] = None,
                 isPublished: Boolean,
                 views: Int = 0
               )

object Post {

  def mapperTo(
                id: Option[Int],
                title: String,
                content: String,
                createdAt: LocalDateTime,
                author: Option[String],
                category: Option[String],
                updatedAt: Option[LocalDateTime],
                likesCount: Int,
                authorId: Option[Int],
                isPublished: Boolean,
                views: Int
              ) = apply(id,
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
