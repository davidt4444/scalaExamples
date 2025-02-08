package com.ads.sparkpostmapper

import com.ads.sparkpostmapper.mappings.PostMapper
import scala.concurrent.ExecutionContext


object Main extends App {
  implicit val ec: ExecutionContext = ExecutionContext.global
  val mapper = new PostMapper()
  mapper.postToJpost

}