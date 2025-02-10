package com.ads.flinkpostmapper.models

import java.time.LocalDateTime

case class JPost(
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

object JPost {
  // Here you might add companion object methods if needed, like factory methods, etc.
}