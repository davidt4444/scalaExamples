package com.ads.sparkpostmapper.models
import java.sql.Timestamp

  case class Post(
    id: Option[Long] = None,
    uniqueId: String,
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
