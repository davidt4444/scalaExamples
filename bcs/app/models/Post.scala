// /bcs/app/models/Post.scala
package com.ads.bcs.app.models

import java.time.Instant

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
