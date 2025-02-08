val scala3Version = "2.13.16"

lazy val root = project
  .in(file("."))
  .settings(
    name := "SparkPostMapper",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies += "org.scalameta" %% "munit" % "1.0.0" % Test
  )
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.5.4" % "provided",
  "org.apache.spark" %% "spark-sql" % "3.5.4",
  "mysql" % "mysql-connector-java" % "8.0.33",
)
fork := true
   javaOptions ++= Seq(
   "-Djava.security.manager=allow",
   //https://stackoverflow.com/questions/73465937/apache-spark-3-3-0-breaks-on-java-17-with-cannot-access-class-sun-nio-ch-direct
    "--add-opens=java.base/java.lang=ALL-UNNAMED",
    "--add-opens=java.base/java.lang.invoke=ALL-UNNAMED",
    "--add-opens=java.base/java.lang.reflect=ALL-UNNAMED",
    "--add-opens=java.base/java.io=ALL-UNNAMED",
    "--add-opens=java.base/java.net=ALL-UNNAMED",
    "--add-opens=java.base/java.nio=ALL-UNNAMED",
    "--add-opens=java.base/java.util=ALL-UNNAMED",
    "--add-opens=java.base/java.util.concurrent=ALL-UNNAMED",
    "--add-opens=java.base/java.util.concurrent.atomic=ALL-UNNAMED",
    "--add-opens=java.base/sun.nio.ch=ALL-UNNAMED",
    "--add-opens=java.base/sun.nio.cs=ALL-UNNAMED",
    "--add-opens=java.base/sun.security.action=ALL-UNNAMED",
    "--add-opens=java.base/sun.util.calendar=ALL-UNNAMED",
    "--add-opens=java.security.jgss/sun.security.krb5=ALL-UNNAMED"
   )
