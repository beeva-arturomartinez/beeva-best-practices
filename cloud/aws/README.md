# AWS Best Practices
At this point we're going to talk about...

![AWS ](static/aws.png  "AWS Best Practices")

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

IAM enables you to control who can do what in your AWS Account. There are 10 best practices that you can realize related to IAM

####Create individual IAM users

Don't use your AWS root account credentials to access AWS, and don't give your credentials to anyone else. Instead, create individual users for anyone who needs access to your AWS account. Create an IAM user for yourself as well, give that user administrative privileges, and use that IAM user for all your work.

By creating individual IAM users for people accessing your account, you can give each IAM user a unique set of security credentials. You can also grant different permissions to each IAM user. If necessary, you can change or revoke an IAM user's permissions any time. (If you give out your AWS root credentials, it can be difficult to revoke them, and it is impossible to restrict their permissions.)

#### Use groups to assign permissions to IAM users

Instead of defining permissions for individual IAM users, it's usually more convenient to create groups that relate to job functions (administrators, developers, accounting, etc.), define the relevant permissions for each group, and then assign IAM users to those groups. All the users in an IAM group inherit the permissions assigned to the group. That way, you can make changes for everyone in a group in just one place. As people move around in your company, you can simply change what IAM group their IAM user belongs to.

#### Grant least privilege

When you create IAM policies, follow the standard security advice of granting least privilege—that is, granting only the permissions required to perform a task. Determine what users need to do and then craft policies for them that let the users perform only those tasks.

It's more secure to start with a minimum set of permissions and grant additional permissions as necessary, rather than starting with permissions that are too lenient and then trying to tighten them later.

Defining the right set of permissions requires some research to determine what is required for the specific task, what actions a particular service supports, and what permissions are required in order to perform those actions.

#### Configure a strong password policy for your users

If you allow users to change their own passwords, require that they create strong passwords and that they rotate their passwords periodically. On the Account Settings page of the IAM console, you can create a password policy for your account. You can use the password policy to define password requirements, such as minimum length, whether it requires non-alphabetic characters, how frequently it must be rotated, and so on.

#### Enable MFA for privileged users

For extra security, enable multifactor authentication (MFA) for privileged IAM users (users who are allowed access to sensitive resources or APIs). With MFA, users have a device that generates a unique authentication code (a one-time password, or OTP) and users must provide both their normal credentials (like their user name and password) and the OTP. The MFA device can either be a special piece of hardware, or it can be a virtual device (for example, it can run in an app on a smartphone).

#### Use roles for applications that run on Amazon EC2 instances

Applications that run on an Amazon EC2 instance need credentials in order to access other AWS services. To provide credentials to the application in a secure way, use IAM roles. A role is an entity that has its own set of permissions, but that isn't a user or group. Roles also don't have their own permanent set of credentials the way IAM users do. In the case of Amazon EC2, IAM dynamically provides temporary credentials to the EC2 instance, and these credentials are automatically rotated for you.

When you launch an EC2 instance, you can specify a role for the instance as a launch parameter. Applications that run on the EC2 instance can use the role's credentials when they access AWS resources. The role's permissions determine what the application is allowed to do.

#### Delegate by using roles instead of by sharing credentials

You might need to allow users from another AWS account to access resources in your AWS account. If so, don't share security credentials, such as access keys, between accounts. Instead, use IAM roles. You can define a role that specifies what permissions the IAM users in the other account are allowed, and from which AWS accounts the IAM users are allowed to assume the role.

#### Rotate credentials regularly

Change your own passwords and access keys regularly, and make sure that all IAM users in your account do as well. That way, if a password or access key is compromised without your knowledge, you limit how long the credentials can be used to access your resources. You can apply a password policy to your account to require all your IAM users to rotate their passwords, and you can choose how often they must do so.

#### Use policy conditions for extra security

To the extent that it's practical, define the conditions under which your IAM policies allow access to a resource. For example, you can write conditions to specify a range of allowable IP addresses that a request must come from, or you can specify that a request is allowed only within a specified date range or time range. You can also set conditions that require the use of SSL or MFA (multifactor authentication). For example, you can require that a user has authenticated with an MFA device in order to be allowed to terminate an Amazon EC2 instance.

#### Lock away your AWS account (root) access keys

You use an access key (an access key ID and secret access key) to make programmatic requests to AWS. However, do not use your AWS account (root) access key. The access key for your AWS account gives full access to all your resources for all AWS services, including your billing information. You cannot restrict the permissions associated with your AWS account access key.

Therefore, protect your AWS account access key like you would your credit card numbers or any other sensitive secret. Here are some ways to do that:

- If you don't already have an access key for your AWS account, don't create one unless you absolutely need to. Instead, use your account email address and password to sign in to the AWS Management Console and create an IAM user for yourself that has administrative privileges.
- If you do have an access key for your AWS account, delete it. If you must keep it, rotate (change) the access key regularly. To delete or rotate your AWS account access keys, go to the Security Credentials page in the AWS Management Console and sign in with your account's email address and password. You can manage your access keys in the Access Keys section.
- Never share your AWS account password or access keys with anyone. The remaining sections of this document discuss various ways to avoid having to share your account credentials with other users and to avoid having to embed them in an application.
- Use a strong password to help protect account-level access to the AWS Management Console. For information about managing your AWS account password, see Changing the AWS Account ("root") Password.
- Enable AWS multifactor authentication (MFA) on your AWS account. For more information, see Using Multi-Factor Authentication (MFA) in AWS.

---
### SNS
---
### SQS
---

### References

* [Architecting for the Cloud: Best Practices](http://media.amazonwebservices.com/AWS_Cloud_Best_Practices.pdf) 
* [IAM Best Practices](http://docs.aws.amazon.com/IAM/latest/UserGuide/best-practices.html)

___

[BEEVA](http://www.beeva.com) | 2015
