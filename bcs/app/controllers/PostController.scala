// /bcs/app/controllers/PostController.scala
package com.ads.bcs.app.controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import com.ads.bcs.app.models.Post
import com.ads.bcs.app.service.PostService
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PostController @Inject()(postService: PostService, cc: ControllerComponents)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  import PostJsonSupport._ // Assuming you have this defined as before

  def createPost = Action.async(parse.json[Post]) { request =>
    postService.createPost(request.body).map { post =>
      Created(Json.toJson(post))
    }
  }

  def findAllPosts = Action.async {
    postService.findAllPosts.map { posts =>
      Ok(Json.toJson(posts))
    }
  }

  def findPost(id: Long) = Action.async {
    postService.findPostById(id).map {
      case Some(post) => Ok(Json.toJson(post))
      case None => NotFound("Post not found")
    }
  }

  def updatePost(id: Long) = Action.async(parse.json[Post]) { request =>
    postService.updatePost(id, request.body).map {
      case Some(updatedPost) => Ok(Json.toJson(updatedPost))
      case None => NotFound("Post not found or could not be updated")
    }
  }

  def deletePost(id: Long) = Action.async {
    postService.deletePost(id).map { deleted =>
      if (deleted) NoContent else NotFound("Post not found")
    }
  }
}