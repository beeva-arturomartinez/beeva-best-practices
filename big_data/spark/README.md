![alt text](static/spark-logo.png "SPARK")

# Spark Best Practices

## Index

* [Writing applications](#writing-applications)
* [Launching applications](#launching-applications)
* [Spark SQL](#spark-sql)
* [Spark Streaming](#spark-streaming)
* [Tuning and debugging](#tuning-and-debuging)

### Writing applications

#### SparkContext
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

### Launching applications

* Driver/executors
* Spark-submit options
* Cluster types

### Spark SQL

* DataFrames
* Hive ...

### Spark Streaming

* DStreams
* Checkpointing
* MaxRate and backpressure options

### Tuning and debugging

* Tuning spark configuration
* Debugging spark applications

### Spark on EMR
* Launching applications
* Connecting to cluster instances and spark UI


### Markdown examples

Some code examples:
````javascript
    var http = require('http');
    http.createServer(function (req, res) {
      res.writeHead(200, {'Content-Type': 'text/plain'});
      res.end('Hello World\n');
    }).listen(1337, '127.0.0.1');
    console.log('Server running at http://127.0.0.1:1337/');
````

### The Two Towers

> Blockquotes are very handy in email to emulate reply text.
> This line is part of the same quote.

A Remarkable idea


### The Return of the King

A nice table

| Tables        | Are           | Cool  |
| ------------- |:-------------:| -----:|
| col 3 is      | right-aligned | $1600 |
| col 2 is      | centered      |   $12 |
| zebra stripes | are neat      |    $1 |


### References

* [Link](http://www.url.to) Description
* [oficialsite.org](http://www.oficialwebsite.org) API & Docs
* [Overapi Cheatsheet](http://overapi.com/example/) Cheatsheet

___

[BEEVA](http://www.beeva.com) | 2015
