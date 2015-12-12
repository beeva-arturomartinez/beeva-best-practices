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

---
## Introduction
Angel Lorigados
---
## Cloud Best Practices 

There are 5 main best practices that you must know in order to use AWS with success.

### Design for failure and nothing will fail

Assume that your hardware will fail. Assume that outages will occur. Assume that some disaster will strike
your application. Assume that you will be slammed with more than the expected number of requests per second some
day. Assume that with time your application software will fail too. By being a pessimist, you end up thinking about
recovery strategies during design time, which helps in designing an overall system better. 

AWS specific tactics for implementing this best practice:

1. Use ElasticIps for failover gracefully combine with Elastic Load Balancers and Route 53 service to minimize service outages.
2. Utilize multiple Availability Zones. By
deploying your architecture to multiple availability zones, you can ensure highly availability. Active multi AZs in Amazon RDS to avoid database outages. Many AWS services are multi AZ by default like S3,DynamoDB
3. Use Elastic Load Balancers to distribute traffic accross multiple AZs. 
4. Maintain an Amazon Machine Image so that you can restore and clone environments very easily in
a different Availability Zone.
5. Utilize Amazon CloudWatch (or various real-time open source monitoring tools) to get more
visibility and take appropriate actions in case of hardware failure or performance degradation.
6. Setup an Auto scaling group to maintain a fixed fleet size so that it replaces unhealthy Amazon EC2
instances by new ones.
7. Utilize Amazon EBS and set up cron jobs so that incremental snapshots are automatically uploaded
to Amazon S3 and data is persisted independent of your instances. Define a snapshot rotation policy for store older snapshot in Amazon Glacier
8. Utilize Amazon RDS and set the retention period for backups, so that it can perform automated
backups.

---

### Decouple your components

Decoupling your components, building asynchronous systems and scaling horizontally become very important in the
context of the cloud. Design your architecture in order to support multiple consumers and multiple producers for your app data.

AWS specific tactics for implementing this best practice:

1. Use Amazon SQS to isolate components 
2. Use Amazon SQS as buffer between components
3. Design every component such that it expose a service interface and is responsible for its own
scalability in all appropriate dimensions and interacts with other components asynchronously
4. Bundle the logical construct of a component into an Amazon Machine Image so that it can be
deployed more often
5. Make your applications as stateless as possible. Store session state outside of component use redis or memcached

---

### Implement elasticity

Elasticity is the ability of an app to adapt for their use. Can be implemented manually or automatically. 

To implement “Elasticity”, one has to first automate the deployment process and streamline the configuration and build
process. This will ensure that the system can scale without any human intervention.


AWS specific tactics for implementing this best practice:

1. Define Auto-scaling groups for different clusters using the Amazon Auto-scaling feature in Amazon
EC2.
2. Monitor your system metrics (CPU, Memory, Disk I/O, Network I/O) using Amazon CloudWatch and
take appropriate actions (launching new AMIs dynamically using the Auto-scaling service) or send
notifications.
3. Store and retrieve machine configuration information dynamically using DynamoDB
4. Design a build process such that it dumps the latest builds to a bucket in Amazon S3; download the
latest version of an application from during system startup or use AWS Code Deploy.
5. Bundle Just Enough Operating System (JeOS) and your software dependencies into an Amazon
Machine Image so that it is easier to manage and maintain. Pass configuration files or parameters at
launch time and retrieve user data and instance metadata after launch.

 


---

### Think parallel

You need te design your processes and workflow thinking in the concept of cloud parallelization. In order to achieve maximum performance and throughput, you should leverage request parallelization. Multi-threading
your requests by using multiple concurrent threads will store or fetch the data faster than requesting it sequentially.

AWS specific tactics for implementing this best practice:

1. Multi-thread your Amazon S3 requests 
2. Multi-thread your Amazon DynamoDB requests
3. Stream your information to multiple consumers with Amazon Kinesis and Amazon DynamoDB Streams.
4. Create a JobFlow using the Amazon Elastic MapReduce Service for each of your daily batch processes
(indexing, log analysis etc.) which will compute the job in parallel and save time.
5. Use the Elastic Load Balancing service and spread your load across multiple web app servers
dynamically
6. Use the Elastic Cache service to share user sessions information with all EC2 instances.
7. Use AWS Lambda to paralellize the execution of multiple functions for the same event.

---

### Keep dynamic data closer to the compute and static data closer to the end-user

In general it’s a good practice to keep your data as close as possible to your compute or processing elements to reduce
latency. In the cloud, this best practice is even more relevant and important because you often have to deal with
Internet latencies. Moreover, in the cloud, you are paying for bandwidth in and out of the cloud by the gigabyte of data
transfer and the cost can add up very quickly. 

AWS specific tactics for implementing this best practice:

1. Utilize the same Availability Zone to launch a cluster of machines
2. Use Amazon S3 to store all of the static files used by your application
3. Create a distribution of your Amazon S3 bucket and let Amazon CloudFront caches content in that
bucket 


---


## Cloud Migration
Angel Lorigados
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
---
### EC2
---
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
### DynamoDB
---

### References

* [Link](http://media.amazonwebservices.com/AWS_Cloud_Best_Practices.pdf) Architecting for the Cloud: Best Practices

___

[BEEVA](http://www.beeva.com) | 2015
