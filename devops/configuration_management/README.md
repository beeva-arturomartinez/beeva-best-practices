# Configuration Management

Configuration Management is the set of processes that are used to ensure proper operation of the Informational Systems as they were previously defined, in an automated and repeatable way.

Using CM we will be able to keep versioned changes that we make in our infrastructure and applications to automatically transfer this changes between environments quickly and safely.

To do this work, there are a variety of tools available which facilitate the task of managing the lifecycle of the infrastructure, both physical and virtual (eg,  [TheForeman](www.theforeman.org)), and tools for automating software and services installation and configuration (eg, [Puppet](puppetlabs.com)).

![alt text](https://github.com/beeva/beeva-best-practices/blob/master/static/horizontal-beeva-logo.png "BEEVA")

# Index

* [Objectives](#objectives)
* [Architecture, technologies and tools](#)
	* [Puppet](#puppet)
	* [Foreman](#foreman)
	* [r10k](#r10k)
	* [Cloudformation](#cloudformation)
	* [Yafct](#yafct)
* [Preparing the environment](#preparing-the-environment)
	* [Puppet Modules](#puppet-modules)
		* [Proposed architecture](#proposed-architecture)
		* [Git workflow](#git-workflow)
		* [Roles and Profiles](#roles-and-profiles)
	* [Dynamic environments r10k](#dynamic-environments-r10k)
		* [Puppetfile](#puppetfile)
		* [Hieradata](#hieradata)
			* [When to use hiera](#when-to-use-hiera)
			* [Interpolation of variables in hiera](#interpolation-of-variables-in-hiera)
	* [Hiera Encryption](#hiera-encryption)
		* [Introduction and purpose](#introduction-and-purpose)
		* [Creating Puppetmaster keys and Hiera configuration](#creating-puppetmaster-keys-and-hiera-configuration)
		* [Eyaml files process encryption](#eyaml-files-process-encryption)
			* [Password encryption](#password-encryption)
			* [File encryption](#file-encryption)
			* [Application properties encryption](#application-properties-encryption)
	* [Deployment of static files](#deployment-of-static-files)
		* [Installation and purpose](#installation-and-purpose)
	* [Example Hiera hierarchy](#example-hiera-hierarchy)
* [Foreman Stack creation](#foreman-stack-creation)
	* [Extra definitions](#extra-definitions)
	* [Hostgroups (runlist)](#hostgroups-runlist)
	* [Hosts (runlist)](#hosts-runlist)
		* [Templates](#templates)
		* [Init Script](#init-script)
* [Puppet Module Documentation](#puppet-module-documentation)

---------------

clean up 

---------------

---
### Hiera Encryption

Certain information stored in Hiera is sensitive information. This information should not be stored in clear, so it is interesting to explore some encryption mechanisms to securize such data.

#### Introduction and purpose

Eyaml library provides a backend for Hiera which provides an encryption method for sensible the information stored in Hiera.

Eyaml is a ruby gem which can be easily installed via terminal.

```bash
$ gem install hiera-eyaml
```

#### Creating Puppetmaster keys and Hiera configuration

Once the gem has been installed, create a new keypair launching:

```bash
$ eyaml createkeys
```

This command creates a new keypair in the default location, which should be */etc/puppet/secure/keys*. Each Puppetmaster should have its own keypair, so it's advisable to rename the files in order to match the name of that Puppetmaster.

To be able to edit already encrypted files, it requires the following config file.

```
$ cat /etc/eyaml/config.yaml

  pkcs7_private_key: '/etc/puppet/secure/keys/foremandev_private_key.pkcs7.pem'
  pkcs7_public_key:  '/etc/puppet/secure/keys/foremandev_public_key.pkcs7.pem'

```

In order to use eyaml backend, we must change the following file and restart the Puppetmaster (the following hierarchy is proposal, but could change depending on concrete system)

```
$ cat /etc/puppet/hiera.yaml
---
:backends:
  - eyaml
  - yaml
  - file
:hierarchy:
  - %{::hostname}
  - %{::fqdn}
  - %{::rol}/%{::version}
  - %{::rol}
  - %{::proyecto}
  - common

:eyaml:
  :datadir: "/var/lib/hiera/%{::environment}/%{::app_tier}"
  :pkcs7_private_key: /etc/puppet/secure/keys/foremandev_private_key.pkcs7.pem
  :pkcs7_public_key:  /etc/puppet/secure/keys/foremandev_public_key.pkcs7.pem

:yaml:
  :datadir: "/var/lib/hiera/%{::environment}/%{::app_tier}"

:file:
  :datadir: "/var/lib/hiera/%{::environment}/%{::app_tier}/files/%{calling_module}"

:logger: console
```

#### Eyaml files process encryption

One of the objectives of this process is that each individual Puppetmaster owns its own cypher keys, so no other Puppetmaster could decrypt those files.

To encrypt the content of yaml files and the Puppetmaster be able to decrypt those files, it's necessary to copy the public Puppetmaster key locally and create a config file.

```
$ cat ~/.eyaml/config_foremandev.yaml
---
pkcs7_public_key: '~/.eyaml/keys/foremandev_public_key.pkcs7.pem'
```

First, it is needed to export the environment variable EYAML_CONFIG, refering the configuration file used to perform the encryption.

```
$ export EYAML_CONFIG=/home/javier/.eyaml/config_foremandev.yaml
```

From that moment, eyaml can be used as follows

##### Password encryption

```
$ eyaml encrypt -p
[hiera-eyaml-core] Loaded config from /home/javier/.eyaml/config_foremandev.yaml
Enter password: ****
string: ENC[PKCS7,MIIBeQYJKoZIhvcNAQcDoIIBajCCAWYCAQAxggEhMIIBHQIBADAFMAACAQAwDQYJKoZIhvcNAQEBBQAEggEArUq53WZzWwhCveNujzECRmhumHQ7i3NtPIaHBF0oXc35II4l5r4RezH5InHnmvnignjFfZMkxq9K5L4DKAKUyWYoGcRcjcLiZ5QLgM4CmCn0+73xobHHyGoRRFLp8jEW9OQ9RlFFTSV5VikgJdvFgBH5j9eIIJgldUPSPV0aVkhEOzccYJnd6UiJXKYeDESkJiH3wNAKUDZXUo9PIAS5atwEEZ8MDv3wDtanvyL3ZtBqyXb2BspdZTWMdO2WrNHlMxZJoZ60jSUqKH6bK/Ks9LavJS3rqwWBBiC84Q9EFyTaqCNTcvCQfdlqA8wRWsL/JGx+tsXWTaWCsfLnWzi/TjA8BgkqhkiG9w0BBwEwHQYJYIZIAWUDBAEqBBC2DnixWHy2i0950mmwKifCgBCyNfMt0vEe2Adsx/2ls1Wb]

OR

block: >
	ENC[PKCS7,MIIBeQYJKoZIhvcNAQcDoIIBajCCAWYCAQAxggEhMIIBHQIBADAFMAACAQAw
	DQYJKoZIhvcNAQEBBQAEggEArUq53WZzWwhCveNujzECRmhumHQ7i3NtPIaH
	BF0oXc35II4l5r4RezH5InHnmvnignjFfZMkxq9K5L4DKAKUyWYoGcRcjcLi
	Z5QLgM4CmCn0+73xobHHyGoRRFLp8jEW9OQ9RlFFTSV5VikgJdvFgBH5j9eI
	IJgldUPSPV0aVkhEOzccYJnd6UiJXKYeDESkJiH3wNAKUDZXUo9PIAS5atwE
	EZ8MDv3wDtanvyL3ZtBqyXb2BspdZTWMdO2WrNHlMxZJoZ60jSUqKH6bK/Ks
	9LavJS3rqwWBBiC84Q9EFyTaqCNTcvCQfdlqA8wRWsL/JGx+tsXWTaWCsfLn
	Wzi/TjA8BgkqhkiG9w0BBwEwHQYJYIZIAWUDBAEqBBC2DnixWHy2i0950mmw
	KifCgBCyNfMt0vEe2Adsx/2ls1Wb]
```

This command shows the config file from which the content is encrypted  and prompts the user for the password. Command's output is the string to be put into the hiera eyaml.

This way, passwords are encrypted by environment, can be versioned safely and only the corresponding puppetmaster can decipher it.

When the content is copied into the eyaml file, the parameters string or block must be replaced (for readability, block parameter used to be the preferred option) by the parameter name which will be queried from puppet's manifests.

##### File encryption

Hiera-eyaml can also encrypt entire files, which is idea, for example, for private apache certificates. The procedure is as follows:

```
$ eyaml encrypt -f super_secret_file

[hiera-eyaml-core] Loaded config from /home/javier/.eyaml/config_foremandev.yaml
string: ENC[PKCS7,MIIBeQYJKoZIhvcNAQcDoIIBajCCAWYCAQAxggEhMIIBHQIBADAFMAACAQAwDQYJKoZIhvcNAQEBBQAEggEAeLuKPJaGv9ylIXtkR4EZ0lSzjmLayK2sZv+2CT5zgUUZ0tMXallbOR3FB+2cACGO2uXcElZDFgpb0ZGTlGZ1Ts0/U8PEaKGdHec+Au0PYNwzdJNdRZFR+yKYY7HXSCB1E6276V78AEtKfkVSmG8FdszDXedFK8+JJ72LzTCtaIcoKzuiGG2VURztmK98Cvunf3rTGbrb5lbWdm6W+NJzg9ZJXq7Y/F1GD0jY9DAkREoFa8VgWNepdQ5w8ZH0ov8Pju6gXEtAyeB7KpAV05vRRhXsnfFWDDDX8fr/PWo6R4I4NvQI1HE1hFU+tZPX7YQWIDIpwMo4GhHVtnZ+9ccIiTA8BgkqhkiG9w0BBwEwHQYJYIZIAWUDBAEqBBCnL9yAq+6rY7xBpQoR8yf5gBDbRsT+7JPv8ODVnAoiiF8l]

OR

block: >
	ENC[PKCS7,MIIBeQYJKoZIhvcNAQcDoIIBajCCAWYCAQAxggEhMIIBHQIBADAFMAACAQAw
	DQYJKoZIhvcNAQEBBQAEggEAeLuKPJaGv9ylIXtkR4EZ0lSzjmLayK2sZv+2
	CT5zgUUZ0tMXallbOR3FB+2cACGO2uXcElZDFgpb0ZGTlGZ1Ts0/U8PEaKGd
	Hec+Au0PYNwzdJNdRZFR+yKYY7HXSCB1E6276V78AEtKfkVSmG8FdszDXedF
	K8+JJ72LzTCtaIcoKzuiGG2VURztmK98Cvunf3rTGbrb5lbWdm6W+NJzg9ZJ
	Xq7Y/F1GD0jY9DAkREoFa8VgWNepdQ5w8ZH0ov8Pju6gXEtAyeB7KpAV05vR
	RhXsnfFWDDDX8fr/PWo6R4I4NvQI1HE1hFU+tZPX7YQWIDIpwMo4GhHVtnZ+
	9ccIiTA8BgkqhkiG9w0BBwEwHQYJYIZIAWUDBAEqBBCnL9yAq+6rY7xBpQoR
	8yf5gBDbRsT+7JPv8ODVnAoiiF8l]
```

Same issue here than the previous point, when the content is copied into the eyaml file, the parameters string or block must be replaced by the parameter name which will be queried from puppet's manifests

##### Application properties encryption

Hiera-eyaml allows also encrypt application properties content that must be privated. This is special interesting for passwords or other database sensible information.

For this, we will use hiera-eyaml and augeas as follows.

```
define profiles::properties_decrypt (
  $ensure                 	= absent,
  $property_file          	= undef,
  $lens                   	= 'Properties.lns',
  $changes                	= [],
  ) {
  case $ensure {
	present: {
  	augeas { $property_file:
    	lens  	=> $lens,
    	incl  	=> $property_file,
    	changes   => $changes,
    	}
	}
	absent: {
  	notify { 'profiles::properties_decrypt::ensure set to absent - Noop': }
	}
	default: {
  	notify { 'profiles::properties_decrypt::ensure must be present|absent': }
    	}
	}
}
```

### Deployment of static files

#### Installation and purpose



## Example Hiera hierarchy

This is an possible Hiera hierarchy

```
/var/lib/hiera/
└── clientapi_development
	└── dev
       	├── common.yaml
    	       ├── dev-clientapi-portal.eyaml
    	       └── files
        	    ├── httpd
        	    │   ├── common.d
        	    │   │   ├── dev-clientapi-portal.conf
        	    │   │   ├── httpd.conf
        	    │   │   └── ssl.conf
        	    │   └── dev-clientapi-portal.d
        	    │   	└── dev-clientapi-portal.conf
        	    └── tomcat
               	    └── common.d
                     	├── logging.properties
                	       └── server.xml
```

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

#### Templates

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

#### Init script

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
# Puppet Module Documentation
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
