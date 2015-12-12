# AWS Best Practices
At this point we're going to talk about...

[Replace this logo] ![BEEVA](https://github.com/beeva/beeva-best-practices/blob/master/static/horizontal-beeva-logo.png "BEEVA")

## Index

* [Introduction](#introduction)
* [Cloud Best Practices](#cloud-best-practices)  
* [Cloud Migration](#cloud-migration)
* [Account Management](#account-management)
* [User Management](#user-management)
* [Security](#security)
* [Storage](#storage)
* [Services](#services)
* [EC2](#ec2)
* [DynamoDB](#dynamodb)

---
## Introduction
---
## Cloud Best Practices 
---
## Cloud Migration
---
## Account Management
---
## User Management
---
## Security
---
## Storage
---
## Services

### EC2
Amazon EC2 is one of the primary AWS service which provide the Infrastructure as a Service(IaaS) and it is related directly to several topics such as networking, security, ... so apply best practices in EC2 is very important.

##### EC2 Instance Management
* Follow the Standard Naming convention to the EC2 Instances like : project, environment, content / purpose of the instance, ... Example : _dev_memento_apis_ or _dev_memento_spark_
* Usually is necessary an Elastic IP, this way stop and start of the Instance will not impact the connections.
* Stop not needed Instances (for non productive environments),  the best approach here is doing it automatically so after work timetable and weekends the instances go down.
* Communicate always using the Private IP’s when possible to avoid the data transfer charges.
* When previous rule is not possible, use Public DNS, since when the comunication is from Internet or other regions, it will resolve as Public IP, however, if it is from same region it will resolve as Private IP automatically.
* Don’t keep any important data in ephemeral storage. You will lost the data, when you stop and start the instance.
* Use termination protection when necessary to prevent the accidental termination of EC2 instances.
* Select carefully instance types depending on the applications requirements (computation, memory, ...) running in it, AWS is always presenting new instance types and sometimes new ones are better and cheapper than old ones you need to be always up to date in that matter, in this [link](https://aws.amazon.com/ec2/pricing/) you can see instance types and prices so you can compare, be careful and select the target Region since the price is not the same.
* Setup the CloudWatch Monitoring alarms for CPU and Instance Status and configure the alerts for your support mail ID, to notify for any CPU issues or instance reachability issues.
* Periodically, run AWS trusted Advisor and see the underutilized instances, so that you can degrade them to the lower instance types

##### EBS Management
* Follow a convention for naming EBS volumes, the names should be self-explanatory, so with the name you can find out what is in there : volume purpose, environment, ... for example: _kafka_zookeeper_topics_data_volume_
* Take EBS snapshots regularly. The frequency will be based on the used of the volume, the more use, the more frequent, also it is useful before deploy a new release or a big change.
* Copy EBS snapshots to your DR or backup region to maximum availability.
* Keep at least last 15 snapshots of EBS volumes so you are able to rollback your data to certain older date.
* Perform regulary cleaning for the unused Volumes to avoid the confusion and cost saving.
* Be aware of the impact of the snapshots so always take your volumes snapshots at non-business hours.

##### AMI Management
* Create a Golden AMI, once you setup & configure your EC2 machine completely.
* Create Production AMI’s backup frecuently (depending on the frequency of changes you made to the Instance).
* Follow a standard naming convention when you name the AMIs. Best practice is append current date for the AMI name and Description. For example : _APIs_prod_AMI_201501212_
* Perform frecuently clenaing for not used AMIs.
* Copy the AMIs to the DR or backup region (if the project has) whenever you take the new AMI.

##### Security
* As in the previous sections, follow the Standard Naming convention for Key Pairs and Security Groups, this is always useful so  you can understand the purpose in at first glance.
* **Never** ever share any of your Key Pairs unless it is really needed.
* Create separate Keys and Groups for each group of Instances. Never use a single Key Pairs or Security Group for your entire region. 
* Perform frecuently clenaing and remove unused Security Groups / Key Pairs, specially remove the keys of users who are not part of the project or organization. Also unused ports (perphaps opened temporary) should be part of the cleaning.
* Use IAM permissions and never ever share the master key.
* Recycle the keys periodically, let's say half-yearly or yearly and create the new keys including master key.
* You shouldn't use a default SG for any instance.
* Never open any port to the entire public (Source 0.0.0.0/0) unless really needed, for example for ports like 80, 443 etc. Have in mind that for internal communications you can open ports using security groups rather than directly IP’s. Be aware that Public and Private IP’s keep changes.
* Create the SG before launching EC2 instance and assign it while launching it because you can’t change it once you have launched it.

### DynamoDB

DynamoDB best feature is the high performance, this guideline is a summary of best practices to for maximizing performance and minimizing throughput costs.

* Select the right primary key, this can be simple (partition key) or composite (partition key and sort key), have in mind that DynamoDB divides a table's items into multiple partitions, and distributes the data primarily based upon the partition key value. The provisioned throughput associated with a table is also divided evenly among the partitions, with no sharing of provisioned throughput across partitions so keep your workload spread evenly across the partition key values, distributing requests across partition key values distributes the requests across partitions.
* Cache Popular Items
* Provision the right amount of read and write capacity: to understand this let's use and example: consider a situation where you need to bulk-load 20 million items into a DynamoDB table. Assume that each item is 1 KB in size, resulting in 20 GB of data. This bulk-loading task will require a total of 20 million write capacity units. To perform this data load within 30 minutes, you would need to set the provisioned write throughput of the table to 11,000 write capacity units, in this scenario, DynamoDB will create 11 partitions, each with 1000 provisioned write capacity units. To understand the Provisioned Throughput in depth go to [this link](http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/ProvisionedThroughputIntro.html).
* Test Your Application At Scale
* Items: DynamoDB items are limited in size ([see Limits in DynamoDB](http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Limits.html)), however, there is no limit in terms of the number of items in a table so rather than storing large amount of data in attribute values in an item, consider one or more of these application design alternatives.
* Query and Scan: avoid Sudden Bursts of Read Activity since this can consume the provisioned read capacity, instead of using a large Scan operation, you can use Reduce Page Size and/or Isolate Scan Operations to minimize the impact of a scan on a table's provisioned throughput.
* Take Advantage of Parallel Scans
* Local secondary index lets you define an alternative sort key for your data, so they can be very useful but don't create them on attributes that you won't often query since they can contribute to increased storage and I/O costs.
* Regarding indexes, be aware of the cost of I/O operation to maintaining them, specially for tables with heavy write activity, If you need to index the data in such a table, evaluate copying the data to another table with any necessary indexes, and query it there.
* Optimize Frequent Queries To Avoid Fetches: to get the fastest queries with the lowest possible latency, project all of the attributes that you expect those queries to return
* Take Advantage of Sparse Indexes: for any item in a table, DynamoDB will only write a corresponding index entry if the index sort key value is present in the item. If the sort key does not appear in every table item, the index is said to be sparse.
* Global secondary indexes can be a powerful feature since they since them let you define alternative partition key and sort key attributes for your data so you can query a global secondary index in the same way that you query a table, always having in mind that it's very important to distribute the read and write activity evenly across the entire table based on the indexes so you should choose partition keys and sort keys that have a high number of values relative to the number of items in the index. Rremember that global secondary indexes do not enforce uniqueness.



### Scaling (ELBs y ASGs)
---
### S3
---
### IAM
---
### SNS
---
### SQS
---

### References

* [Link](http://www.url.to) Description
* [oficialsite.org](http://www.oficialwebsite.org) API & Docs
* [Overapi Cheatsheet](http://overapi.com/example/) Cheatsheet

___

[BEEVA](http://www.beeva.com) | 2015
