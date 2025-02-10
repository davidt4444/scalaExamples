package com.ads.flinkpostmapper.models

import java.sql.Timestamp

// Case class definition for Post
case class Post(
    id: Option[Int] = None,
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
