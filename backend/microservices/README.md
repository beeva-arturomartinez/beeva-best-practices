# Microservices Best Practices

This is how we work with Microservices at BEEVA.

![BEEVA](https://github.com/beeva/beeva-best-practices/blob/master/static/horizontal-beeva-logo.png "BEEVA")

## Index

1. [Introduction to microservices](#1-introduction-to-microservices)

2. [Microservice architectures](#2-microservice-architectures)

    2.1. [Overview for a microservice architecture](#21-overview-for-a-microservice-architecture)

    2.2. [Components for a microservice architecture](#22-components-for-a-microservice-architecture)

    2.3. [Communication between microservices](#23-communication-between-microservices)

3. [Microservices with Spring Cloud](#3-microservices-with-spring-cloud)

    3.1. [Introduction](#31-introduction)

    3.2. [Config Server](#32-config-server)

    3.3. [Eureka](#33-eureka)

    3.4. [Zuul](#34-zuul)

    3.5. [Ribbon](#35-ribbon)

    3.6. [Bus](#36-bus)

    3.7. [Sidecar](#37-sidecar)

4. [Using docker to deploy microservices](#4-using-docker-to-deploy-microservices)

5. [A reference architecture for microservices in AWS](#5-a-reference-architecture-for-microservices-in-aws)

6. [References](#6-references)

## 1. Introduction to microservices
---

A microservice is an independent process with a series of characteristics:
- Loosely coupled
- Small logic
- Single purpose
- Deployed independently
- Deployed in an automated way

Applications built with a microservice-oriented architecture have a set of advantages:

- Logic isolated in functional blocks
- Easier maintenance
- Selective deploys without outages
- Easier integration with cloud platforms (for example, fine grained scalation)

This document addresses some of the challenges that arise when using an architecture based on microservices:

- Microservice discovery : how many microservices exist and where are they?
- Central configuration
- Error resiliency and failover

All these challenges are addressed using a good infrastructure, able to orchestrate all existing microservices in our application.

## 2. Microservice architectures
---

### 2.1 Overview for a microservice architecture

### 2.2 Components for a microservice architecture

### 2.3 Communication between microservices

## 3. Microservices with Spring Cloud
---

### 3.1 Introduction

Rather than starting from the scratch, we have some reference architectures already working. One of them is the Netflix architecture, that was released as Open Source as part of the Netflix OSS stack.

This architecture has been proven to be a very successful architecture and for this reason Spring decided to wrap its components in a Spring project, called Spring Cloud.

Together with Spring Boot, Spring Cloud offers a really easy way to deploy the infrastructure's core needed for orchestrate a microservice-based application, including:

 - A centralized configuration server
 - A service discovery component (Eureka)
 - A router (Zuul)
 - A load balancer module (Ribbon)

Complemented with some additional components that enhance the features offered:

 - A failover mechanism based on the circuit breaker pattern (Hystrix)
 - A communication bus to send messages to all microservices (Spring Cloud bus)
 - A bridge module to allow polyglot microservices, implemented in programming languages different than Java (Sidecar)

The picture below illustrates a general overview of this architecture:

![Spring Cloud Overview](static/spring_cloud_overview.png)

### 3.2 Config Server

One of the most important orchestration modules is the Config Server. Its main purpose is to centralize the configuration for all microservices using the architecture.

We recommend to use git as source for the properties and to have an optimal file organization, we provide some hints:

- Use an isolated repository for spring cloud components (zuul, eureka)
- Use folders to categorize property files instead of a flat folder for every file
- Use profiles to swap easily between environment configurations.
- Do not duplicate properties, group the common ones and store them in files at the root level
- If you need to use several GIT repositories, associate them to microservice name patterns

Additionally, we recommend to avoid Single Points Of Failure (SPOF) providing several configuration servers behind a load balancer (see section 5 for details).

#### A sample configuration

 A sample configuration file (bootstrap.yml) for a Spring Cloud Config Server is shown below:

 ``` YAML
 spring:
   application:
       name: configsrv
   cloud:
       config:
         server:
           git:
             uri: git@mydomain:mygroup/mydefaultrepository.git
             repos:
               myrepository1:
                 pattern: project1-*
                 uri: git@mydomain:mygroup/myrepository1.git
                 searchPaths: functionality-*
               myrepository2:
                 pattern: project2-*
                 uri: git@mydomain:mygroup/myrepository2.git
                 searchPaths: functionality-*
           defaultLabel: master
 ```

 and the associated file structure for one of the repositories, according to the previous configuration is listed below:

``` java
    application.yml
    application-local.yml
    application-pre.yml
    application-pro.yml
    project1-arbitraryname/functionality-1/microservice1.yml
    project1-arbitraryname/functionality-1/microservice1-local.yml
    project1-arbitraryname/functionality-1/microservice1-pre.yml
    project1-arbitraryname/functionality-1/microservice1-pro.yml
    project2-anotherarbitraryname/functionality-2/microservice2.yml
    project2-anotherarbitraryname/functionality-2/microservice2-local.yml
    project2-anotherarbitraryname/functionality-2/microservice2-pre.yml
    project2-anotherarbitraryname/functionality-2/microservice2-pro.yml
```

> When a microservice with name _microservice1_ starts with profile 'local', it will request to the config server all needed configuration files. According to the configuration above, config server will server the following files:

    - application.yml
    - application-local.yml
    - microservice1.yml
    - microservice1-local.yml


### 3.3 Eureka

Eureka is the module responsible for both service registering and service discovering.

It is divided into two diferent modules:
- Eureka Server
- Eureka Client

#### Eureka Server

It is a REST service that is used for service discovery, load balancing and failover of middle-tier servers. For a complete reference of all Eureka REST operations, see the next link: https://github.com/Netflix/eureka/wiki/Eureka-REST-operations

Eureka Server can be deployed in a Single Instance configuration or (recommended) in a Cluster Configuration.

When Eureka is deployed as a Cluster, all the nodes in the cluster needs to communicate with each other to synchronize their metadata, so when a new microservice registers with one of the Eureka Server nodes, all of the metadata information will be replicated along the cluster. Each Eureka Server node shares the same information for each microservice. To do that, each Eureka Node stores a Registry with the information of each registered microservice. Thereby, each microservice is responsible for providing a heartbeat to let Eureka knows that it is up and running. When some microservice fails in providing this heartbeat signal, it is removed from the registry.

A high level example of a cluster configuration, can be seen below:

![Eureka Cluster Overview](static/eureka_cluster_overview.png)

Eureka server exposes the information of all of the microservices registered throughout the REST API and the Web UI.

Eureka server is included in Spring Cloud as an annotation: @EnableEurekaServer

#### Eureka Client

It is a Java-based client component that wraps all the neccesary requests up to interact with the REST API from the Eureka Server.

When a microservice wants to register in Eureka Server, it has to provide some metadata information as host and port, health URI, etc...
As mentioned above it has to also provide a heartbeat every 30 seconds to let the cluster knows that is up and running. These 30 seconds can be configured and adapted to the required needs.

This eureka client is included in Spring Cloud as an annotation: @EnableEurekaClient

#### Communication between Eureka Clients And Eureka Servers

The key point to understand the behaviour of Eureka is to know how clients and server communicate with each others.
At this point we have seen a few different actors that are involved in this game:
- Eureka Server
- Eureka Clients
- Ribbon Clients

All of them use different cache mechanisms, so it is interesting to know the different steps that are carried out when a microservice is registered in Eureka Server, and how long it takes this microservice to be avaible to recieve requests.

Both @EnableEurekaServer and @EnableEurekaClient provide an implementation of Eureka Client, along with an implementation of Ribbon Load Balancer. So, even when we are not directly using some of these clients, they are there for us.

##### Step 1: Microservice registration in Eureka Server

When the Microservice starts up it sends the first heartbeat to Eureka Server. At this point, the server still doesn't know anything about the microservice so it sends back a response with the **404** status code. The microservice is forced to be registered so it sends a **new request** containing all the necessary information like host, port, etc...

Now, the microservice is registered in Eureka Server, but it is not available yet to receive incoming requests. The microservice is not ready until it sends the heartbeat again to the server. This sending happens, by default, **30 seconds** after registration, so the microservice will not be reachable before this period.

This default time can be configured in the microservice eureka client through the property: **`eureka.instance.leaseRenewalIntervalInSeconds`**

##### Step 2: Server Response and Cached Items

##### Step 3: Client Cache

##### Step 4: Ribbon LoadBalancer Cache



### 3.4 Zuul

### 3.5 Ribbon

### 3.6 Bus

### 3.7 Sidecar

So far, we have only considered microservices implemented in JAVA programming language. What could I do if we want to build a microservice in a different programming language? the answer is : use the Sidecar module of Spring Cloud, based on Netflix Prana.

![Spring Cloud Sidecar Overview](static/spring_cloud_sidecar.png)

A bridge application, hosted in the same machine that the microservice is responsible of communicating the microservice with the Spring Cloud architecture.

We have to enable an endpoint inside our microservice to allow Eureka to monitor and register the microservice into the architecture. For communications in the other direction, the microservice can retrieve information from Spring Cloud via the bridge application.

> http://[bridge_domain]:[bridge_port]/hosts/[serviceId]

But config server will not be accessible this way unless we register it in Zuul (which is not the default behaviour).

#### Example : Bridge Application

Suppose we configure a bridge application in port 10001.

**Application.java**
```java

    package com.demo;

    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
    import org.springframework.cloud.netflix.sidecar.EnableSidecar;

    @SpringBootApplication
    @EnableEurekaClient
    @EnableSidecar
    public class Application
    {
        public static void main( String[] args )
        {
            SpringApplication.run(Application.class, args);
        }

    }

```

**application.yml**
```YAML

    server:
      port: 10001 # Port used by bridge application

    spring:
      application:
        name: microservice-sidecar-bridge # Name that will be registered in eureka for the bridge application

    sidecar:
      port: 3000 # Microservice's port
      health-uri: http://localhost:${sidecar.port}/health # Microservice's heartbeat url
```

#### Example : Microservice in node.js

**app.js**
```javascript

    var express = require('express');
    var bodyParser = require('body-parser');
    var app = express();
    app.use(bodyParser.json());
    app.use(bodyParser.urlencoded({ extended: false }));

    var session = require('express-session');

    // Populates req.session
    app.use(session({
        resave: false,
        saveUninitialized: false,
        secret: 'atomic mouse'
    }));

    var path = require('path');
    var favicon = require('serve-favicon');
    var cookieParser = require('cookie-parser');

    var swig = require('swig');
    var util = require('util');
    var restify = require('restify');

    // App engine
    app.engine('html', swig.renderFile);

    // view engine setup
    app.set('views', path.join(__dirname, 'views'));
    app.set('view engine', 'html');

    app.use(cookieParser());
    app.use(express.static(path.join(__dirname, 'public')));

    var port = 3000;

    // Start server
    app.listen(port);

    // Exports the configuration
    module.exports = {
        app: app,
    };

    //Add routes to detect the enabled endpoints
    require('./routes/index');
```

**/routes/index.js**
```javascript

    var appJs = require('../app.js');

    /* GET home page. */
    appJs.app.route('/').get(function(req, res) {
      res.send("This is the home page of node.js application");
    });

    appJs.app.route('/health').get(function(req,res){
        res.set("Content-Type","application/json");
        res.status(200).send({
            "status":"UP"
        });
    });
```

Assuming that Zuul is started at port 8989 and with name _zuul_, an invocation to the node.js microservice would be possible :

> http://localhost:8989/microservice-sidecar-bridge

and from the node.js microservice, retrieval of information about zuul would be possible through:

> http://localhost:10001/hosts/zuul

## 4. Using docker to deploy microservices
---

## 5. A reference architecture for microservices in AWS
---

## 6. References
---

* [Spring Cloud Project Website](http://projects.spring.io/spring-cloud/)
* [Spring Boot](http://projects.spring.io/spring-boot/)

___

[BEEVA](http://www.beeva.com) | 2015
