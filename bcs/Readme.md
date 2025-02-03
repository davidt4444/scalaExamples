The documentation for play could be better
This is a mix of
https://daily.dev/blog/build-rest-api-with-scala-play-framework
and grok prompts to fit the dependencies and structure with the 
repos referenced by the homebrew sbt install.
Those repos are here https://repo1.maven.org/maven2/com/typesafe/akka/
Grok:
https://x.com/i/grok/share/yx2igkHAPLtmOR5XgdPr7LPhJ
https://x.com/i/grok?conversation=1884828555973976255

---------------
basic-content-service
---------------
sbt new playframework/play-scala-seed.g8
The app base is com.ads.bcs.app where ads is the company,
bcs is the project, and app is where the source is 
cd bcs
sbt run
As you add in the packages, for e.g. akka 
the repos are here https://repo1.maven.org/maven2/com/typesafe/akka/
The transitions from examples are below:
//"com.typesafe.slick" %% "slick" % "3.3.3", >>   "org.playframework" %% "play-slick" % "6.1.1",
//"com.typesafe.slick" %% "slick-hikaricp" % "3.3.3", >>   "com.typesafe.slick" %% "slick-hikaricp" % "3.5.1",
//"mysql" % "mysql-connector-java" % "8.0.28", >>   "mysql" % "mysql-connector-java" % "8.0.33"
//"com.typesafe.akka" %% "akka-http" % "10.2.7", >> "com.typesafe.akka" %% "akka-http" % "10.5.3",
//"com.typesafe.akka" %% "akka-stream" % "2.6.18", >> "com.typesafe.akka" %% "akka-stream" % "2.8.8",
//"com.typesafe.play" %% "play-json" % "2.9.2" >> "org.playframework" %% "play-json" % "3.1.0-M1",

There are controllers. PostJsonSupport is for the serializing.
A lot of tutorials and generated code will try to get you to put the routes in the controller.
Those are located in the conf/routes file
Models are self explanatory.
Rather than use application.conf, I used url to keep sensitive stuff separate from the project.
The value is usually a connection string like
jdbc:mysql://user:password@localhost:3306/project_database?useSSL=false
References to functions in the repository are done in the service, which are called in the controller

------------
3.3.5 changes

https://stackoverflow.com/questions/41179532/when-overloading-apply-method-slick-error-message-value-tupled-is-not-a-memb
mapperto function in Post
No apply function needed. It is handled in postjsonsupport
For tupple in PostRepository
(Post.tupled, Post.unapply)
becomes
((Post.mapperTo _).tupled, Post.unapply)

