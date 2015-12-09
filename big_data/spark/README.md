# Spark Best Practices

![alt text](static/spark-logo.png "SPARK")

## Index

* [Writing applications](#writing-applications)
* [Launching applications](#launching-applications)
* [Spark SQL](#spark-sql)
* [Spark Streaming](#spark-streaming)
* [Tuning and debugging](#tuning-and-debuging)

### Writing applications

* SparkContext
* Transformations and actions
* Broadcast variables and accumulators

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
