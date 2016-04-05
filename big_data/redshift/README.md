![alt text](static/redshift-logo.png "REDSHIFT")

# Amazon Redshift Best Practices

## Index

* [Overview](#overview)
  * [Usage Patterns](#usage-patterns)
  * [Architecture](#architecture)
  * [Columnar Storage and Compression](#columnar-storage-and-compression)
  * [Workload Management](#workload-management)
* [Designing Tables](#designing-tables)
  * [Distribution Style](#distribution-style)
  * [Sort Key](#sort-key)
  * [Encoding](#encoding)
  * [Constraints](#constraints)
* [References](#references)

### Overview

Amazon Redshift is a cloud-based massively parallel processing (MPP) columnar data warehouse for SQL data analytics over datasets ranging from
few hundreds of gigabytes to petabytes.

Redshift's three main design concepts are:
 - Parallel/distributed architecture
 - Columnar storage
 - Automation of common administrative tasks (provisioning, monitoring, backing up, securing...)

#### Usage Patterns

Redshift **ideal use case is large dataset online analytical processing (OLAP)** with BI tools or SQL clients.

You **SHOULD NOT** use it for:

 - **Small datasets (<100 GB)**: Redshift is architected for parallel and distributed processing over large amounts of data. For small datasets you'll get better performance using other technologies like PostgreSQL, MySQL, Aurora, etc...
 - **Transactional / Operational repositories (OLTP)**: Redshift is intended for analytical purposes and informational data. If you need a transactional system, it would be better to choose a RDMS or NoSQL database.
 - **Unstructured data**: data stored in Redshift must be defined by a formal structured schema. If you want to analyze/explore unstructured data or develop ETL tasks it is better to choose technologies like EMR, Spark, Pig, Hadoop, etc...
 - **BLOB data**: Redshift is no designed for binary data storing. If you want to store binary objects there are better alternatives such as Amazon S3.
 - **SELECT * FROM customers WHERE customer_id=123**: The Horror! The Horror! Redshift is designed for analytical workloads and uses columnar storage. Accessing arbitrary specific rows and retrieving all columns at the same time is not what you want to be doing with Redshift. For applications that access data this way there are better alternatives like RDMS or NoSQL databases.

#### Architecture

Redshift has a **Master-Slave architecture pattern** where client applications (e.g. BI tools or SQL clients) connect via **JDBC/ODBC** with
a **Leader node** that manages communication, executes query plans and distributes workloads among compute nodes.

![alt text](static/redshift-architecture.png "REDSHIFT")

**Compute nodes** store data and execute the compiled code returning intermediate results to the leader node for final aggregation. Each one has
its own dedicated CPU, memory and disk. Data is replicated across the cluster and automatically backed up in S3 for fault-tolerance purposes. Compute nodes are connected in a high-bandwidth private network with close proximity that client applications can never access directly.

Each compute node is partitioned into **slices** depending on the node size of the cluster. The number of slices determines the parallelism level of the cluster. There are two instance families for compute nodes:

 - **Dense Storage (DS)**: intended for huge datasets where disk capacity is key driver for your architecture (less slices per GB)
 - **Dense Compute (DC)**: in exchange for less disk capacity, these instances provide SSD disks and more RAM and CPU. Thus, you should use
 DC nodes when computing capacity is more important than disk capacity (more slices per GB)

In order to scale-out/in/up/down your cluster you can perform a **Resize** at any time. This process will:

 1. Put your cluster in read-only mode
 2. Provision a new cluster with the desired capacity in parallel (you only pay for the active cluster)
 3. Copy all data from the old cluster to the new one
 4. Redirect your URL to point to the new cluster (it doesn't change)
 5. Drop the old cluster

##### Columnar Storage and Compression

Columnar storage is a key design principle for the majority of analytical databases and it drastically improves performance in OLAP use cases with large datasets.

Row-wise databases save data as tuples where data blocks are stored sequentially and contain every field (column) of that row. This model is well suited for OLTP operational applications where transactions frequently read or write one or few rows at a time and use most of the fields of that rows.

On the other hand, OLAP informational applications tend to read to large amounts of rows using a few fields as dimensions and/or aggregations. Thus, a column-wise
schema, where data is stored in blocks that contain values of the same column for different rows, is a better alternative as it **avoids reading non necessary columns and reduces the amount of data that needs to be retrieved**.

In addition to this, **this model lets us choose the best compression algorithm for each column type** reducing the volume of the data at rest, transit and memory. For instance, a text column could be compressed as LZO while a numeric column could use a DELTA encoding.

##### Workload Management

In order to manage concurrency and resource planning Redshift provides execution queues. Each queue can be configured with the following parameters:

 1. **Slots**: number of concurrent queries that can be executed in this queue.
 2. **Working memory**: percentage of memory assigned to this queue.
 3. **Max. Execution Time**: the amount of time a query is allowed to run before it is terminated.

Queries can be routed to different queues using Query Groups and User Groups. As a rule of thumb, is considered a best practice to have separate queues for long running resource-intensive queries and fast queries that don't require big amounts of memory and CPU.

### Designing Tables

Working with large datasets requires a high degree of resource optimization and we often find I/O, CPU, Memory or Disk bottlenecks due to wrong table designs.

In order to optimize your cluster you should understand and properly design your tables with these three concepts in mind:

 1. Data distribution (sharding)
 2. Sorting
 3. Encoding (compression)

#### Distribution Style

Table distribution style determines **how data is distributed across compute nodes** and we have three options:

##### Key
A column acts as distribution key (DISTKEY). As a rule of thumb you should choose a column that:

 1. **Is uniformly distributed.** Otherwise skew data will cause deviations in the amount of data that will be stored in each compute node leading to undesired situations where some slices will process bigger amounts of data than others causing bottlenecks.
 2. If this table is related with dimensions tables (star-schema), it is better to choose as DISTKEY the field that acts as the JOIN field with the larger dimension table. This way, related data (same join-field values) will reside in the same node, reducing the amount of data that needs to be broadcasted through the network.

##### Even
Default. **Data is distributed automatically using a round-robin algorithm.** This is better when the table does not take part in joins or it is not clear which column can act as DISTKEY.

##### All
The whole **table is replicated in every compute node**. This distribution style is intended for small tables that don't change too often. For instance, small dimension tables are good candidates. Having data available in each compute node also reduces the amount of data that needs to be broadcasted through the network when executing joins.

#### Sort Key
Sort keys define in which order data will be stored. When you load data in a table for the first time it will be stored in order and Redshift will register metadata with max and min sortkey values for each disk block (**zone map**). This zone map will be used for the query planner to prune the search tree and drastically improve execution plans for range-restricted queries.

As rule of thumb, you should select columns with range filtering in WHERE clauses. For instance, timestamp columns tend to be good candidates.

If you add unsorted rows to a table that is already sorted is a best practice to perform **VACUUM SORT ONLY [tablename]** in order to obtain the maximum performance from your sortkey.

The are two kinds of sort keys in Redshift: Compund and Interleaved.

##### Compound Keys

This is the default mode. You can specify more than one column as SORTKEY. Data will be sorted using SORTKEY definition order: first column will act as the first order key, second column next and so on.

Zone maps with compound keys provide better performance when pruning occurs in the leading columns and decreases as we move to the trailing ones. Thus, this kind of keys are recommended when there is a clear column candidate mostly used for sorting and filtering data (e.g: a timestamp).

##### Interleaved Sort Keys

Performing ad-hoc multi-dimensional analytics often requires pivoting, filtering and grouping data using different columns as query dimensions. This leads to scenarios where compound key ordering is not flexible enough and performance decreases.

Interleaved Sort Keys is Amazon Redshift implementation for **Z-order curve** ordering. This model is preferable when dealing with muli-dimensional analytics.

#### Encoding
As discussed above, columnar storage let us chose the best compression/encoding model for each row. There are two ways to setup encodings:

1. If you load data into an empty table the for the first time with the **COPY command**, then Redshift will automatically apply the best compression based in a sample of the data (be sure that this data is a good sample of the whole dataset for that table).

2. You can run the **ANALYZE COMPRESSION [tablename]** command at any moment to obtain a list of recommended encodings for your table. This is useful when the COPY command is not an option (for instance, SELECT INSERT). As a best practice, you can load a sample of your data into an empty table, then run ANALYZE COMPRESSION, then create a new table with the recommended encodings and load your dataset into the new table.

**You cannot change column encodings once created**.

#### Constraints

You can create **UNIQUE, PRIMARY KEY and FOREIGN KEY** constraints in Redshift but only with informational purposes. **Redshift does not perform integrity checks for these constraints**. Anyway, creating constraints is a best practice since it provides useful information for the query planner in order to optimize executions.

You can also create **NOT NULL** constraints. **Redshift does enforce NOT NULL column constraints.**

### References

- [Amazon Redshift Documentation](https://aws.amazon.com/documentation/redshift/ "Amazon Redshift Documentation")
- [Optimizing for Star Schemas and Interleaved Sorting on Amazon Redshift](https://blogs.aws.amazon.com/bigdata/post/Tx1WZP38ERPGK5K/Optimizing-for-Star-Schemas-and-Interleaved-Sorting-on-Amazon-Redshift "Optimizing for Star Schemas and Interleaved Sorting on Amazon Redshift")
- [Understanding Interleaved Sort Keys in Amazon Redshift](https://blog.chartio.com/blog/understanding-interleaved-sort-keys-in-amazon-redshift-part-1 "Understanding Interleaved Sort Keys in Amazon Redshift")
- [Top 10 Performance Tuning Techniques for Amazon Redshift](https://blogs.aws.amazon.com/bigdata/post/Tx31034QG0G3ED1/Top-10-Performance-Tuning-Techniques-for-Amazon-Redshift "Top 10 Performance Tuning Techniques for Amazon Redshift")
- [Z Order Curve](https://en.wikipedia.org/wiki/Z-order_curve "Z Order Curve")
