![alt text](static/spark-logo.png "SPARK")

# Spark Best Practices

## Index

* [Writing applications](#writing-applications)
* [Launching applications](#launching-applications)
* [Deploying applications](#deploying-applications)
* [Spark SQL](#spark-sql)
* [Spark Streaming](#spark-streaming)
* [Testing](#testing)
* [Tuning and debugging](#tuning-and-debuging)

### Writing applications

#### SparkContext

SparkContext object represents a connection to the spark computing cluster. Every spark application needs to configure and intialize a SparkContext. A SparkConf class instance can be created and passed to configure SparkContext initialization.

````scala
val conf = new SparkConf()
conf.set("spark.app.name", "MyApp")
conf.set("spark.ui.port", "36000")
val sc = new SparkContext(conf)
````
The list of properties that can be defined can be found [here](#http://spark.apache.org/docs/latest/configuration.html#spark-properties)

Note that these properties can also be set as arguments of spark-submit


#### Transformations and actions

Do not return all the elements of a large RDD back to the driver. Avoid using collect and count on large RDDS, use instead take or takeSample to control the number of elements returned. Be careful using actions like  countByKey, countByValue, collectAsMap.

Chose the transformations in order to minimize the number of shuffles.

Avoid using groupByKey. Consider using the following two functions instead:
* combineByKey can be used when you are combining elements but your return type differs from your input value type.
* foldByKey merges the values for each key using an associative function and a neutral "zero value".
* reduceByKey is preferred to perform an associative reduction operation. For example, rdd.groupByKey().mapValues(_.sum) will produce the same results as rdd.reduceByKey(_ + _) but the latter will perform local sums before sending the values to combine after shuffling.

Avoid using reduceByKey when the output value type differs from the input type of elements to reduce. To reduce all elements into a collection of elements consider using aggregateByKey instead.

Avoid using the flatMap + join + groupBy pattern. When two datasets are already grouped by key and you want to join them and keep them grouped, you can just use cogroup.

One exception to the general rule of trying to minimize the shuffles is when you force them to increase parallelism. For example, when you process a few large unsplittable files and after loading them they have not been splitted into enough partitions to take advantage of all the available cores. In this scenario invoking repartiton with a high number of partitions is preferred.

#### Broadcast variables and accumulators

Broadcast variables are created on the driver, and are read-only from executors. The distribution of these variables across the cluster is made through an efficient p2p broadcast algorithm, so they can be used to distribute large input datasets in an efficient manner. Take into account that once one variable has been defined and broadcasted, its value ca't be updated.

Accumulators are seen as write-only variables on the executors and can be used to implement counters or sums that pushes data back to the driver through an associative operation.

### Launching applications

* Driver/executors
* Spark-submit options
* Cluster types

### Deploying applications

For Scala and Java, the preferred option is to pack the spark application and all its dependencies into an assembly JAR and pass this jar to spark-submit. In scala you can create a build.sbt and use the sbt assembly plugin as follows:

````scala
import AssemblyKeys._

name := "ExampleApp"

version := "1.0"

scalaVersion := "2.10.4"

val SPARK_VERSION = "1.5.2"

libraryDependencies += "org.apache.spark" %% "spark-core" % SPARK_VERSION
libraryDependencies += "org.apache.spark" %% "spark-mllib" % SPARK_VERSION
libraryDependencies += "org.apache.spark" %% "spark-sql" % SPARK_VERSION
libraryDependencies += "org.apache.spark" %% "spark-streaming" % SPARK_VERSION
libraryDependencies += "com.google.code.gson" % "gson" % "2.3"
libraryDependencies += "commons-cli" % "commons-cli" % "1.2"

resolvers += "Akka Repository" at "http://repo.akka.io/releases/"

assemblySettings

mergeStrategy in assembly := {
  case m if m.toLowerCase.endsWith("manifest.mf")          => MergeStrategy.discard
  case m if m.toLowerCase.matches("meta-inf.*\\.sf$")      => MergeStrategy.discard
  case "log4j.properties"                                  => MergeStrategy.discard
  case m if m.toLowerCase.startsWith("meta-inf/services/") => MergeStrategy.filterDistinctLines
  case "reference.conf"                                    => MergeStrategy.concat
  case _                                                   => MergeStrategy.first
}
````

### Spark SQL

* DataFrames
* Hive ...

### Spark Streaming

It is much more efficient for large windows to use the extended windowed transformations passing an inverse function like:
* reduceByWindow(reduceFunc: (T, T) ⇒ T, invReduceFunc: (T, T) ⇒ T, windowDuration: Duration, slideDuration: Duration): DStream[T]
* reduceByKeyAndWindow(reduceFunc: (V, V) ⇒ V, invReduceFunc: (V, V) ⇒ V, windowDuration: Duration, slideDuration: Duration, partitioner: Partitioner, filterFunc: ((K, V)) ⇒ Boolean): DStream[(K, V)].


Use the pattern foreachRDD-foreachPartition to reuse external connections.

Bad:
````scala
dstream.foreachRDD { rdd =>
  val connection = createNewConnection()
  rdd.foreach { record =>
    connection.send(record) // executed at the worker
  }
}
````

Good:
````scala
dstream.foreachRDD { rdd =>
  rdd.foreachPartition { partitionOfRecords =>
    val connection = createNewConnection()
    partitionOfRecords.foreach(record => connection.send(record))
    connection.close()
  }
}
````

Even better:
````scala
dstream.foreachRDD { rdd =>
  rdd.foreachPartition { partitionOfRecords =>
    val connection = ConnectionPool.getConnection()
    partitionOfRecords.foreach(record => connection.send(record))
    ConnectionPool.returnConnection(connection)  // return to the pool for future reuse
  }
}
````

Windowed stateful transformations requires enabling checkpointing. Checkpointing also can be optionally enabled for recovery from driver failures. To enable checkpointing you need to use StreamingContext.getOrCreate as follows:
````scala
def functionToCreateContext(): StreamingContext = {
    val ssc = new StreamingContext(...)   
    val lines = ssc.socketTextStream(...)
    ...
    ssc.checkpoint(checkpointDirectory)
    ssc
}

val context = StreamingContext.getOrCreate(checkpointDirectory, functionToCreateContext _)
````

If the cluster resources is not large enough for the streaming application to process data as fast as it is being received, the receivers can be rate limited by setting a maximum rate limit in terms of records/sec. This can be achieved setting the configuration parameter spark.streaming.receiver.maxRate (spark.streaming.kafka.maxRatePerPartition for Direct Kafka approach). From spark 1.5 you can set spark.streaming.backpressure.enabled to let Spark Streaming automatically figure out the rate limits and dynamically adjust them if the processing conditions change.

### Testing
For testing you can create fixtures that initializes the spark context and loads the data/creates the streams to start processing each test. If you are using python you need to explicitly load the pyspark path prior to the initialization of the SparkContext. You can use the following function to load pyspark into the python path:

````python
def add_pyspark_path(spark_home):
    """Add PySpark to the library path based on the value of SPARK_HOME. """
    try:
        os.environ["SPARK_HOME"] = spark_home
        sys.path.append(os.path.join(spark_home, 'python'))
        py4j_src_zip = glob(os.path.join(spark_home, 'python',
                                         'lib', 'py4j-*-src.zip'))
        if len(py4j_src_zip) == 0:
            raise ValueError('py4j source archive not found in %s'
                             % os.path.join(spark_home, 'python', 'lib'))
        else:
            py4j_src_zip = sorted(py4j_src_zip)[::-1]
            sys.path.append(py4j_src_zip[0])
    except KeyError:
        print("""SPARK_HOME was not set. please set it. e.g.
        SPARK_HOME='/.../spark' ./bin/pyspark [program]""")
        exit(-1)
    except ValueError as e:
        print(str(e))
        exit(-1)
````


### Tuning and debugging

* Tuning spark configuration
* Debugging spark applications

### Spark on EMR
* Launching applications
* Connecting to cluster instances and spark UI


### References

* [Link](http://www.url.to) Description
* [oficialsite.org](http://www.oficialwebsite.org) API & Docs
* [Overapi Cheatsheet](http://overapi.com/example/) Cheatsheet

___

[BEEVA](http://www.beeva.com) | 2015
