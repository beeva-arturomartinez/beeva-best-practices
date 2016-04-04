![alt text](static/redshift-logo.png "REDSHIFT")

# Amazon Redshift Best Practices

## Index

* [Overview](#overview)
* [Designing Tables](#designing-tables)
* [Workload Management](#workload-management)

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

### Designing Tables

#### Distribution Key

#### Sort Key

#### Encoding

### Workload Management
