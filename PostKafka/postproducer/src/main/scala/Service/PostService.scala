// /postproducer/src/service/PostService.scala
package com.ads.postproducer.service

import com.ads.postproducer.models.Post
import com.ads.postproducer.repository.PostRepository

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PostService @Inject()(postRepository: PostRepository)(implicit ec: ExecutionContext) {
  import java.time.Instant;
  import java.sql.Timestamp;

  def createPost(post: Post): Future[Post] = {
    val newPost = post.copy(createdAt = Timestamp.from(Instant.now())) // Ensure createdAt is set
    postRepository.insert(newPost).map(_ => newPost.copy(id = Some(0))) // Placeholder for DB-generated ID
  }

  def findAllPosts: Future[Seq[Post]] = postRepository.findAll

  def findPostById(id: Long): Future[Option[Post]] = postRepository.findById(id.toInt)

  def updatePost(id: Long, updatedPost: Post): Future[Option[Post]] = {
    val postWithTimestamp = updatedPost.copy(updatedAt = Some(Timestamp.from(Instant.now())))
    postRepository.update(id.toInt, postWithTimestamp).flatMap {
      case true => postRepository.findById(id.toInt) // Return updated post if update was successful
      case false => Future.successful(None) // Return None if update failed
    }
  }

  def deletePost(id: Long): Future[Boolean] = postRepository.delete(id.toInt)
}

// Companion object for easy instantiation in tests or for dependency injection configuration
object PostService {
  def apply(postRepository: PostRepository)(implicit ec: ExecutionContext): PostService = new PostService(postRepository)
}