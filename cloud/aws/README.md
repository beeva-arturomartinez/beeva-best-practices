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

There is no written rule about the account management when a new project is created. Although the first approach for small projects could be to create only one account for all the environments, it has to be known that some limits (number of EIP or EC2 instances and services (i.e. DynamoDB, IAM, Security Groups) are shared through the entire account. This could lead to problems in production when a limit is reached by someone in development.

Said that, it is a BEEVA best practice to separate projects by environment, creating one account for environment (development, maybe QA, staging and production). If for several causes different accounts can't be created, the environments has to be marked by different VPCs in order to isolate them.

There is one central account for user management and corporative issues, which has the consolidated billing for all the accounts. It is in this account where the monitoring and Continuous Integration platforms are tipically allocated. In order to use it, the VPCs in the remain ones can be connected fisically by VPC Peering with the master account.

The name standarizarion used in BEEVA is the following:

```
<project>.<environment>.aws@beeva.com with name <Proyect> <Environment> | BEEVA
```

This means that when a new account is created, a new alias for the corporative mail has to be created, forwarding all the mail to *sistemas@beeva.com*.


## User Management
---

Users in the account can be created with the Identity and Access Management (IAM) service. Following the previous section, where the creation of different accounts per environment, all subordinated to a master account for the billing and corporative tools, the same can be said about user management.

Not only the billing is consolidated into the master account. IAM users are managed with this account too, providing the assumption of the desired role with one single login. 

![Delegated user management](static/delegated.png "Delegated user management")

There is a github project for performing this, named [Anwbis](https://github.com/beeva/anwbis), based on the examples shown in the AWS blog: [How to enable cross account access to the AWS Management Console](https://blogs.aws.amazon.com/security/post/Tx70F69I9G8TYG/How-to-enable-cross-account-access-to-the-AWS-Management-Console).

### User creation

The minimum set of permissions rule apply when a new user is created. The BEEVA convention for the naming is the same as the corporative email without the @beeva.com, so i.e. for a new user named *john.doe@beeva.com*, the user *john.doe* is created in the master account. For external users, the rule is to writte *john.doe.contractor*.

The user is created with the MFA flag marked as true, so he can login into the different accounts using his multifactor authentication device (tipically [Google Authenticator](https://support.google.com/accounts/answer/1066447?hl=es), but tipically there is no permission to login into the master account in the AWS Management Console. 

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

Note the use of the IAM variable *aws:username*, in order to provide a reusable policy for the entire organization.

### Delegated roles

#### Steps in the child account

As all the users in the project has to have the right permissions to use the accounts, several IAM roles can be created in the child account. The name convention is to use a role named:

```
 <environment>-<project>-delegated-<role_name>
```

For compatibility with anwbis, the role_name is advised to be *admin*, *developer*, *user* or *devops*.

In the role, the minimum permissions for the kind of user has to be provided. See [IAM](#IAM) section for more information about how to create the role.

#### Steps in the master account

In order to provide the delegated use, a group named 

```
corp-<project>-master-<role_name> 
```

has to be created in the master account, where role name is tipically *admin*, *developer*, *devops* or *user*. It has only a policy named *Delegated_Roles* with the following structure:

```
{
  "Version": "2012-10-17",
  "Statement": [{
    "Effect": "Allow",
    "Action": ["sts:AssumeRole"],
    "Resource": [
        "arn:aws:iam::************:role/dev-<project>-delegated-<role>",
        "arn:aws:iam::************:role/pre-<project>-delegated-<role>",
        "arn:aws:iam::************:role/pro-<project>-delegated-<role>",
        "arn:aws:iam::************:role/val-<project>-delegated-<role>"
      ], 
    "Condition" : { "Null" : { "aws:MultiFactorAuthAge" : "false" } }
  }]
}
``` 

Where *role* is *admin*, *developer*, *devops* or *user*. In this example there are four environments for the project, but this policy could be extended with all the needed roles.

The users belonging to a project are attached to the group with the set of permissions needed, and they can login and attack the AWS API for the desired account.

#### IAM Credentials and Anwbis

Each user has a set of credentials (Access Key and Secret Key) with are not useful unless a MFA token is provided. In order to use the AWS cli (or other SDK), it is recomended that the file *~/.aws/credentials* is present and with the following content:

```
[default]
aws_access_key_id=<AccessKey>
aws_secret_access_key=<SecretKey>
```

When the user want to get a set of valid credentials with anwbis (version 1.2.1, at the time of the writting), he can do it by typing the following: 

```
anwbis  --profile <profile> -p <project> -e <environment> -r <role_name> --region <region_to_use>
```

The profile section is only needed if the credentials are not located on the default profile. If no region is provided, *eu-west-1* is taken into account.

This creates a new section into the *.aws/credentials* file, with a valid session token.

```
[default]
aws_access_key_id=<AccessKey>
aws_secret_access_key=<SecretKey>

[<project>-<environment>-<role_name>]
aws_access_key_id = <DelegatedAccessKey>
aws_secret_access_key = <DelegatedSecretKey>
aws_session_token = <DelegatedSessionToken>
region = <DefaultRegion>
```

The login into the AWS Management console can be done by providing the flag *-b*, with the browser *chrome* or *firefox* as an argument.

For several scenarios, such as continuous integration, the login can be made without MFA with the flag *--nomfa*. Additional arguments such as *--refresh* (for renewing the token each time) or *--duration* (for changing the duration of the token, from 900 to 3600 seconds) are also provided.

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
