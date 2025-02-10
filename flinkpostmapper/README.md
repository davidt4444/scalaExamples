## sbt project compiled with Scala 3

### Usage

sbt new scala/scala3.g8
FlinkPostMapper
cd flinkpostmapper 

Those repos are here https://repo1.maven.org/maven2/com/typesafe/akka/

Relative paths from the project do not work for the execution of flink, since the jar file is submitted via script, so remember to define the 
com.ads.flinkpostmapper.FlinkConstants.project_sub_dir 
variable with the path of the sub directory containing the projects. This way you can get an output for debugging and you can access the connection strings contained in resource projects.

Otherwise, supply your own path for the output files and connection strings.

# Flink instructions
https://www.geeksforgeeks.org/how-to-install-flink/
## Flink install
Download the binaries
tar -zxvf flink-1.20.0-bin-scala_2.12.tgz
cd flink-1.20.0/
Download the mysql and other connectors
put them in the lib folder before starting
### start local server
./bin/start-cluster.sh

~/flink-1.20.0/bin/start-cluster.sh

After you run that the ui is at localhost:8081
### submit jobs as jars
./bin/flink run examples/streaming/WordCount.jar

~/flink-1.20.0/bin/flink run ./target/scala-2.12/flinkpostmapper_2.12-0.1.0-SNAPSHOT.jar
### Put the commands 
tail log/flink-*-taskexecutor-*.out
### stop the flink local server
./bin/stop-cluster.sh

~/flink-1.20.0/bin/stop-cluster.sh
### For maven projects 
./bin/flink run Test-1.0-SNAPSHOT.jar 
