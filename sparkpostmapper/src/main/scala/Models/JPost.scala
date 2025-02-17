package com.ads.sparkpostmapper.models
import java.time.LocalDateTime

case class JPost(
  id: Option[Integer] = None,
  uniqueId: String,
  title: String,
  content: String,
  createdAt: LocalDateTime,
  author: Option[String] = None,
  category: Option[String] = None,
  updatedAt: Option[LocalDateTime] = None,
  likesCount: Int,
  authorId: Option[Int] = None,
  isPublished: Boolean,
  views: Int
)