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

Redshift ideal use case is online analytical processing (OLAP) with BI tools or SQL clients.

You shouldn't use it for:

 - Small datasets (<100 GB): Redshift is architected for parallel and distributed processing over large amounts of data. For small datasets you'll get better performance using other technologies like PostgreSQL, MySQL, Aurora, etc...
 - Transactional / Operational repositories (OLTP): Redshift is intended for analytical purposes and informational data. If you need a transactional system, it would be better to choose a RDMS or NoSQL database.
 - Unstructured data: data stored in Redshift must be defined by a formal structured schema. If you want to analyze/explore unstructured data or develop ETL tasks it is better to choose technologies like EMR, Spark, Pig, Hadoop, etc...
 - BLOB data: Redshift is no designed for binary data storing. If you want to store binary objects there are better alternatives such as Amazon S3.

#### Architecture

Redshift's architecture is based in a Master-Slave pattern where client applications (e.g. BI tools or SQL clients) connect via JDBC/ODBC with
a Leader node that manages communication, executes query plans and distributes workloads among compute nodes.

![alt text](static/redshift-architecture.png "REDSHIFT")

### Designing Tables

#### Distribution Key

#### Sort Key

#### Encoding

### Workload Management
