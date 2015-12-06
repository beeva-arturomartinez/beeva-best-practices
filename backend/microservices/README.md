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

It is divided in two diferent modules:
- Eureka Server
- Eureka Client

#### Eureka Server

It is a REST service that is used for service discovery, load balancing and failover of middle-tier servers. For a complete reference of all Eureka REST operations, see the next link: https://github.com/Netflix/eureka/wiki/Eureka-REST-operations

Eureka Server can be deployed in a Single Instance configuration or in a Cluster Configuration.

When Eureka is deployed as a Cluster, all the nodes in the cluster needs to communicate with each other to synchronize their metadata, so when a new microservice registers with one of the Eureka Server nodes, all of the metadata information will be replicated along the cluster. Each Eureka Server node shares the same information for each microservice. To do that, each Eureka Node stores a Registry with the information of each registered microservice. Thereby, each microservice is responsible for providing a heartbeat to let Eureka knows that it is up and running. When some microservice fails in providing this heartbeat signal, it is removed from the registry.

A high level example of a cluster configuration, can be seen below:

![Eureka Cluster Overview](static/eureka_cluster_overview.png)

Eureka server exposes the information of all of the microservices registered throughout the REST API and the Web UI.

This eureka client is included in Spring Cloud as an annotation: @EnableEurekaServer

#### Eureka Client

It is a Java-based client component that wraps all the neccesary requests up to interact with the REST API from the Eureka Server.

When a microservice wants to register in Eureka Server, it has to provide some metadata information as host and port, health URI, etc...
As mentioned above it has to also provide a heartbeat every 30 seconds to let the cluster knows that is up and running. These 30 seconds can be configured and adapted to the required needs.

This eureka client is included in Spring Cloud as an annotation: @EnableEurekaClient

#### Communication between Eureka Clients And Eureka Servers

### 3.4 Zuul

### 3.5 Ribbon

### 3.6 Bus

### 3.7 Sidecar

## 4. Using docker to deploy microservices
---

## 5. A reference architecture for microservices in AWS
---

## 6. References
---

* [Sample reference] (http://www.beeva.es) Sample reference description

___

[BEEVA](http://www.beeva.com) | 2015
