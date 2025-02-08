## sbt project compiled with Scala 3

### Usage

sbt new scala/scala3.g8
SparkPostMapper
cd sparkpostmapper 

Add in code
https://x.com/i/grok/share/GIucFS0z6rakHQIoSbl9r3UPm
https://x.com/i/grok/share/Fv65nvG0RmVFD5ethTrMWOZEH

Those repos are here https://repo1.maven.org/maven2/com/typesafe/akka/

After apache spark install directions below are done 
sbt run


# Run below command to install Apache Spark
brew install apache-spark
# Start Spark
spark-shell
# Once you get Scala> prompt, run below command to verify it returns "Hello World!"
scala> val s = "Hello World!"
# If you get error, make sure you set SPARK_HOME and add path as below in your profile
export SPARK_HOME=/usr/local/Cellar/apache-spark/3.1.2/libexec
export PYTHONPATH=/usr/local/Cellar/apache-spark/3.1.2/libexec/python/:$PYTHONP$
source ~/.bash_profile
# Run below command to start all the services
/usr/local/Cellar/apache-spark/3.5.4/libexec/sbin/start-all.sh
# Navigate to http://localhost:8080/ to access Spark Master webpage
# Navigate to http://localhost:4040/ to access Spark Application webpage
# Remember to cleanup and stop all of the services
/usr/local/Cellar/apache-spark/3.5.4/libexec/sbin/stop-all.sh



