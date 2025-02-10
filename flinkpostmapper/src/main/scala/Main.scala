package com.ads.flinkpostmapper

import com.ads.flinkpostmapper.models._
import com.ads.flinkpostmapper.FlinkConstants
import com.ads.flinkpostmapper.sources._
import com.ads.flinkpostmapper.mappings._
import com.ads.flinkpostmapper.sinks._

import java.io.File

import org.apache.flink.streaming.api.scala._


object JPostToPostMapper {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // Assuming you have a source from MySQL, this is a placeholder
    val jPostsStream: DataStream[JPost] = env.addSource(new JPostSource)

    val postStream = jPostsStream.map(new JPostToPostMapperFunction)

    // Here you can do further processing or sink the data back to MySQL or elsewhere
    // Create and configure the sink
    postStream.addSink(new JDBCPostSink)

    env.execute("Save Posts to MySQL")
    
    
    
    //postStream.print() // For debugging purposes
    //val sink = new File(FlinkConstants.project_sub_dir+"/scala_fails/flinkpostmapper/output.txt")
    //if(sink.exists()){
    //  sink.delete()
    //}
    //postStream.writeAsText(sink.toString);
    
  }
}


// JPA Entity class (Java) - Just for reference here, not used in Scala code
// class JPost { ... }
