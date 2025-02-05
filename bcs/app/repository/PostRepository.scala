// /bcs/app/repository/PostRepository.scala
package com.ads.bcs.app.repository

import javax.inject._
import com.ads.bcs.app.models.Post
import slick.jdbc.MySQLProfile.api._
//import play.api.db.slick.DatabaseConfigProvider
import java.time.LocalDateTime;
import scala.concurrent.{ExecutionContext, Future}
import com.typesafe.config.ConfigFactory

class PostRepository @Inject()()(implicit ec: ExecutionContext) {

  private class PostTable(tag: Tag) extends Table[Post](tag, "Post") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def title = column[String]("title")
    def content = column[String]("content")
    def createdAt = column[LocalDateTime]("createdAt")
    def author = column[Option[String]]("author")
    def category = column[Option[String]]("category")
    def updatedAt = column[Option[LocalDateTime]]("updatedAt")
    def likesCount = column[Int]("likesCount")
    def authorId = column[Option[Int]]("authorId")
    def isPublished = column[Boolean]("isPublished")
    def views = column[Int]("views")

    def * = (id.?, title, content, createdAt, author, category, updatedAt, likesCount, authorId, isPublished, views) <> ((Post.mapperTo _).tupled, Post.unapply)
  }
  private val posts = TableQuery[PostTable]

  // Assuming you have a database connection pool set up
//  private val db = Database.forConfig("mysqlDB") // Configuration from application.conf or equivalent
  private val db = Database.forURL(scala.io.Source.fromFile("../../aws-resources/localhost-mac-scala.txt").mkString,
    driver="com.mysql.cj.jdbc.Driver")

  def insert(post: Post)(implicit ec: ExecutionContext): Future[Int] = {
    db.run(posts += post)
  }

  def findAll(implicit ec: ExecutionContext): Future[Seq[Post]] = {
    db.run(posts.result)
  }

  def findById(id: Int)(implicit ec: ExecutionContext): Future[Option[Post]] = {
    db.run(posts.filter(_.id === id).result.headOption)
  }

  def update(id: Int, post: Post)(implicit ec: ExecutionContext): Future[Boolean] = {
    val updateQuery = for { p <- posts if p.id === id } yield (p.title, p.content, p.author, p.category, p.updatedAt, p.likesCount, p.authorId, p.isPublished, p.views)
    db.run(updateQuery.update(post.title, post.content, post.author, post.category, post.updatedAt, post.likesCount, post.authorId, post.isPublished, post.views)).map(_ > 0)
  }

  def delete(id: Int)(implicit ec: ExecutionContext): Future[Boolean] = {
    db.run(posts.filter(_.id === id).delete).map(_ > 0)
  }
}