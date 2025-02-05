// /bcs/app/controllers/PostJsonSupport.scala
package com.ads.bcs.app.controllers

import com.ads.bcs.app.models.Post
import play.api.libs.json._
import play.api.libs.functional.syntax._
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter

object PostJsonSupport {
  private val isoDateFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

  implicit val localDateTimeReads: Reads[LocalDateTime] = Reads[LocalDateTime](js =>
    js.validate[String].flatMap { str =>
      try {
        JsSuccess(LocalDateTime.from(isoDateFormatter.parse(str)))
      } catch {
        case e: Exception => JsError(s"Invalid date-time format: $str")
      }
    }
  )

  implicit val localDateTimeWrites: Writes[LocalDateTime] = Writes[LocalDateTime] { localDateTime =>
    JsString(localDateTime.toString)
  }

  implicit val postReads: Reads[Post] = (
    (JsPath \ "id").readNullable[Int] and
      (JsPath \ "title").read[String].filter(JsonValidationError("Title must be between 5 and 200 characters"))(title => title.length >= 5 && title.length <= 200) and
      (JsPath \ "content").read[String].filter(JsonValidationError("Content must be between 0 and 10000 characters"))(content => content.length >= 0 && content.length <= 10000) and
      (JsPath \ "createdAt").read[LocalDateTime] and
      (JsPath \ "author").readNullable[String].filter(JsonValidationError("Author must be between 0 and 200 characters"))(author => author.forall(_.length <= 200)) and
      (JsPath \ "category").readNullable[String].filter(JsonValidationError("Category must be between 0 and 100 characters"))(category => category.forall(_.length <= 100)) and
      (JsPath \ "updatedAt").readNullable[LocalDateTime] and
      (JsPath \ "likesCount").read[Int] and
      (JsPath \ "authorId").readNullable[Int] and
      (JsPath \ "isPublished").read[Boolean] and
      (JsPath \ "views").read[Int]
    )(Post.apply _)

  implicit val postWrites: OWrites[Post] = Json.writes[Post]
}