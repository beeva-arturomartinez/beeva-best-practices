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
 - Automation of the common administrative tasks (provisioning, monitoring, backing up, securing...)


Redshift's architecture is based in a Master-Slave pattern where client applications (e.g. BI tools or SQL clients) connect via JDBC/ODBC with
a Leader node that manages communication, executes query plans and distributes workloads among compute nodes.

![alt text](static/redshift-architecture.png "REDSHIFT")

### Designing Tables

#### Distribution Key

#### Sort Key

#### Encoding

### Workload Management
