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
---
## Cloud Best Practices 
---
## Cloud Migration
---
## Account Management
---

There is no written rule about the account management when a new project is created. Although the first approach for small projects could be create only one account for all the environments, it has to be known that some limits (number of EIP or EC2 instances and services (i.e. DynamoDB, IAM, Security Groups) are shared through the entire account. This could lead to problems in production when a limit is reached by someone in development.

Said that, it is a BEEVA best practice to separate projects by environment, creating one account for environment (development, maybe QA, staging and production). If for several causes different accounts can't be created, the environments has to be marked by different VPCs, in order to isolate them.

There is one central account for user management and corporative issues, which has the consolidated billing for all the accounts. It is in this account where the monitoring and Continuous Integration platforms are tipically allocated. In order to use it, the VPCs in the remain accounts can be connected fisically by VPC Peering with the master account.



## User Management
---

Users in the account can be created with the Identity and Access Management (IAM) service. Following the previous section, where the creation of different accounts per environment, all subordinated to a master account for the billing and corporative tools, the same can be said about user management.

Not only the billing is consolidated into the master account. IAM users are managed with this account too, providing the assumption of the desired role with one single login. 

![Delegated user management](static/esquema.png "Delegated user management")

There is a github project for performing this, named [Anwbis](https://github.com/beeva/anwbis)

### User creation

The minimum permissions rule apply when a new user is created. The BEEVA convention for the naming is the same as the corporative email without the @beeva.com, so i.e. for a new user named *john.doe@beeva.com*, the user *john.doe* is created in the master account. For external users, the rule is to writte *john.doe.contractor*.

The user is created with the MFA flag marked as true, so he can login into the different accounts using his multifactor authentication device (tipically [Google Authenticator](https://support.google.com/accounts/answer/1066447?hl=es). 

The policy Corp-IAM-Permissions has been created to provide the minimum permission rule for assuming the role in the child accounts. At the time of this writting it is the following (The master account ID has been deleted by obvious reasons):

```
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "Stmt1417088730000",
            "Effect": "Allow",
            "Action": [
                "iam:GetGroup",
                "iam:GetGroupPolicy"
            ],
            "Resource": [
                "arn:aws:iam::***********:group/corp-*"
            ]
        },
        {
            "Sid": "Stmt1417088730001",
            "Effect": "Allow",
            "Action": [
                "iam:ChangePassword",
                "iam:ListMFADevices",
                "iam:GetUser",
                "iam:GetUserPolicy",
                "iam:ListGroupsForUser",
                "iam:ListUserPolicies"
            ],
            "Resource": [
                "arn:aws:iam::************:user/${aws:username}"
            ]
        }
    ]
}

```

### Delegated roles

#### Steps in the child account

As all the users in the project has to have the right permissions to use the accounts, several IAM roles can be created in the child account. The name convention is to use a role named *<environment>-<project>-delegated-<role_name>*

For compatibility with anwbis, the role_name is advised to be *admin*, *developer*, *user* or *devops*

In the role, the minimum permissions for the kind of user has to be provided. See [IAM](#IAM) section for create the role.

#### Steps in the master account

In order to provide the delegated use, a group named *corp-<project>-master-<role_name>* has to be created in the master account. It has only a policy named *Delegated_Roles* with the following structure:

```
{
  "Version": "2012-10-17",
  "Statement": [{
    "Effect": "Allow",
    "Action": ["sts:AssumeRole"],
    "Resource": [
        "arn:aws:iam::************:role/dev-openp-delegated-admin",
        "arn:aws:iam::************:role/pre-openp-delegated-admin",
        "arn:aws:iam::************:role/pro-openp-delegated-admin",
        "arn:aws:iam::************:role/val-openp-delegated-admin"
      ], 
    "Condition" : { "Null" : { "aws:MultiFactorAuthAge" : "false" } }
  }]
}
``` 

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

* [Link](http://www.url.to) Description
* [oficialsite.org](http://www.oficialwebsite.org) API & Docs
* [Overapi Cheatsheet](http://overapi.com/example/) Cheatsheet

___

[BEEVA](http://www.beeva.com) | 2015
