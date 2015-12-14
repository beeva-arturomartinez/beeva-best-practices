# Configuration Management

Configuration Management is the set of processes that are used to ensure proper operation of the Informational Systems as they were previously defined, in an automated and repeatable way.

Using CM we will be able to keep versioned changes that we make in our infrastructure and applications to automatically transfer this changes between environments quickly and safely.

To do this work, there are a variety of tools available which facilitate the task of managing the lifecycle of the infrastructure, both physical and virtual (eg,  [TheForeman](www.theforeman.org)), and tools for automating software and services installation and configuration (eg, [Puppet](puppetlabs.com)).

![alt text](https://github.com/beeva/beeva-best-practices/blob/master/static/horizontal-beeva-logo.png "BEEVA")

## Index

* Hiera Encryption settings
    * Introduction and purpose
    * Creating Puppetmaster keys and Hiera configuration
    * Eyaml files process encryption
        * Passwords
        * Files
        * Application properties
* Deployment of static files
    * Installation and purpose
* Example Hiera hierarchy


* [Foreman Stack creation](#foreman-stack-creation)
* [Extra definitions](#extra-definitions)
* [Hostgroups (runlist)](#hostgroups-runlist)
* [Hosts (runlist)](#hosts-runlist)
* [Templates](#templates)
* [Init Script](#init-script)
* [Puppet Module Documentation](#puppet-module-documentation)

---
## Hiera Encryption Settings

Eyaml library provides us an encryption method for hiera settings.

### Introduction and purpose



### Creating Puppetmaster keys and Hiera configuration



### Eyaml files process encryption



#### Passwords



#### Files



#### Application properties



## Deployment of static files



### Installation and purpose



## Example Hiera hierarchy


---
## Foreman Stack Creation
---
At this point, an environment deployed with *r10k* and all the *hiera* configurations associated with it is presumed to be on the puppetmaster. The next logiacal step is to provide a quick creation of the new infrastructure stacks.

Although there are several ways to do it, such as manual deployment with the Foreman GUI or the official [Hammer](https://github.com/theforeman/hammer-cli) tool, the steps reproduced here use [YAFCT](https://github.com/ITV/YAFCT), due to the complete stack creation options and because it is programmed in Python instead of Ruby. 

Feel free to use another tool from the [Foreman-related tools](http://projects.theforeman.org/projects/foreman/wiki/List_of_Plugins) in the list of supported plugins.

### Extra definitions

In order to improve reusability, an extra definitions file could be created. It is a json file with all the variables written like *@variable_name@: value*, useful for the environment specific variables, such as security groups, AMIs, domains or project.

Here is an example of an extra_definitions.json file:

```
{
    "@ENV@" : "pre",
    "@PROJECT@" : "memento",
    "@DOMAIN@" : "pre.openp.com",
    "@FOREMAN@" : "pre-foreman.@DOMAIN@",
    "@COMPUTE_RESOURCE@" : "pre.openp",   
    "@FOREMAN_ORGANIZATION_ID@": "1",
    "@FOREMAN_LOCATION_ID@": "2",
    "@ENVIRONMENT@" : "environment_name",
    "@OS@": "CentOS-7.0",
    "@MEDIUM@": "CentOS",
    "@PTABLE@": "Kickstart",
    "@INSTANCE1@": "instance1_name",
    "@INSTANCE1_HOSTGROUP_NAME@": "instance1_hostgroup_name",
    "@INSTANCE1_METHOD@" : "create-once",     
    "@INSTANCE2@": "instance2_name",
    "@INSTANCE2_HOSTGROUP_NAME@": "instance2_hostgroup_name",
    "@INSTANCE2_METHOD@" : "delete",     
    "@MEMENTO_BASE_AMI@": "<ami-id>",
    "@PublicSubnetA@": "<subnet-id>",
    "@PrivateSubnetA@": "<subnet-id>",
    "@SecurityGroup1Id@": "<sg1-id>,
    "@SecurityGroup2Id@": "<sg2-id>",
    "@SecurityGroup3Id@": "<sg3-id>",
    "@SecurityGroup4Id@": "<sg4-id>"
}

```

The *METHOD* parameter can be used in order to refer the desired status of the element. Valid values are *create* (always try the creation of the element), *create_once* (try to create it and if it exists, not do anything), *update* (warning: not valid for changing security groups or subnets) or *delete* (try to delete the element in the stack).

When an extra_definitions is used, the command for launching the stack creation is complemented by the *-T* argument (for detokenizing the stack) and the *-D* argument, for passing the JSON. 

Total automation of the process is still a future work, but an example of the command used to launch the stack is provided bellow:

```
./foremanTool.py -c <config_file> -f <foreman_in_config> -m runlist -n <path_to_stack> -T -D "$(cat <path_to_extra_definitions>)"
```

### Hostgroups (runlist)

The runlist are YAML files with the parameters used for calling to the Foreman API. Variables between *@variable_name@* can be used with extra_definitions for reusability:

```
---
# HOSTGROUP STACK FOR HOSTGROUP1
#
# Creates the following hostgroup:
#
#  - hostgroup1
#
# Hostgroup hostgroup1
#
- "name": "@INSTANCE1_HOSTGROUP_NAME@"
  "type": "HostGroup"
  "method": "@INSTANCE1_METHOD@"
  "environment_id": "LookUp(Environment:@ENVIRONMENT@)"
  "puppetclass_ids": ["LookUp(PuppetClass:roles::project::hostgroup1)"]
  "puppet_ca_proxy_id" : "LookUp(Proxy:@FOREMAN@)"
  "puppet_proxy_id" : "LookUp(Proxy:@FOREMAN@)"
  "domain_id": "LookUp(Domain:@DOMAIN@)"
  "architecture_id": "LookUp(Architecture:x86_64)"
  "operatingsystem_id": "LookUp(OperatingSystem:@OS@)"
```

The *LookUp(element:value)* method provides a quick way to give the name of the element instead of the id, when the Foreman API expects the ID.

### Hosts (runlist)

The same YAML file with the right parameters can be provided for the host creation:

```
---
# EC2 HOSTS STACK
#
# Creates the following hosts:
#  - host1
#
# Host host1
#
- "name": "@ENV@-@INSTANCE1_NAME@"
  "organization_id": "@FOREMAN_ORGANIZATION_ID@"
  "type": "Host"
  "method": "@INSTANCE1_METHOD@"
  "template": "./templates/ec2.json"
  "comment": "EC2 instance @ENV@-@INSTANCE_1_NAME@.@DOMAIN@"
  "root_pass": ""
  "compute_resource_id": "LookUp(ComputeResource:@COMPUTE_RESOURCE@)"
  "hostgroup_id": "LookUp(HostGroup:@INSTANCE1_HOSTGROUP_NAME@)"
  "medium_id": "@MEDIUM@"
  "flavor": "t2.small"
  "subnet_id": "@PrivateSubnetA@"
  "security_group_ids":
    - '@SecurityGroup1id@'
    - '@SecurityGroup2id@'
    - '@SecurityGroup3id@'
    - '@SecurityGroup4id@'
  "ami": "@BASE_AMI@"
```
The parameters are passed thanks to the *template* parameter. 

### Templates

Foreman provides the ability to launch instances in different providers, such as bare metal, AWS, GCE, Rackspace, VMWare and so on. The templates functionality in YAFCT is a safe method to provide the parameters the Foreman API expects.

The ec2.json template is provided bellow:

```
{
    "name": "@name@",
    "organization_id": "@organization_id@",
    "location_id": "@location_id@",
    "root_pass": "@root_pass@",
    "comment": "@comment@",
    "compute_resource_id": "@compute_resource_id@",
    "managed": "true",
    "model_id": "",
    "provision_method": "image",
    "interfaces_attributes": {
        "new_interfaces": {
            "name": "",
            "ip": "",
            "provider": "IPMI",
            "mac": "",
            "_destroy": "false",
            "type": "Nic::Managed",
            "domain_id": ""
        }
    },
    "disk": "",
    "overwrite": "false",
    "puppet_status": 1,
    "build": "1",
    "type": "Host::Managed",
    "mac": "",
    "medium_id": "@medium_id@",
    "enabled": "1",
    "hostgroup_id": "@hostgroup_id@",
    "compute_attributes": {
        "flavor_id": "@flavor@",
        "availability_zone": "",
        "subnet_id": "@subnet_id@",
        "security_group_ids": @security_group_ids@,
        "managed_ip": "private",
        "image_id": "@ami@"
    },
    "puppetclass_ids": []
}
```

### Init script

When a new instance is launched, an init or cloud-init script can be provided in order to configure it. It is a BEEVA best practice to name the instances in a particular way in order to provide the initial configuration.

As the *app_tier* is used for mapping the hiera configurations, the standard is the following:

```
<app_tier>-<project>-<subproject>-<version>-.<domain>
```

Then, the init script takes the first characters before the first hypen and executes a puppet run such as:

```
FACTER_app_tier=<app_tier> puppet agent --test (--noop)
```

This procedure is only performed the first time, and if it is a neccessity to change the apptier it can be done by changing the fact in the instance, by hand or with the *factertags* Puppet module.

---
## Puppet Module Documentation
----
Based on the [Puppet Best Practices](http://projects.puppetlabs.com/projects/1/wiki/Puppet_Manifest_Documentation), in order to document the Puppet modules, it is a BEEVA best practice to use the following format in the main classes, defines, roles and profiles: 

```
# Class: nameclass
#
# Description:
#
# Parameters:
#
#    [*parameter1*]        - description
#    [*parameter2*]        - description
#
# Actions:  
#    - Action1
#    - Action2
#
# Requires:
#    - Requirement1
#    - Requirement2
#
# Use:
#
#   class { 'nameclass':
#     parameter1   => value,
#     parameter1   => 'value',
#   }
#
class nameclass {
}
```

Notice the last line of the comments and the class definition are togerher and there is no break line. This is needed for Puppet in order to generate automatic documentation.
___

[BEEVA](http://www.beeva.com) | 2015
