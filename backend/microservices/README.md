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
- Small
- With a single purpose
- Deployed independently
- Deployed in an automated way

Applications built with a microservice-oriented architecture have a set of advantages:

- Logic isolated in functional blocks
- Easier maintenance
- Selective deploys without outages
- Easier integration with cloud platforms (for example, fine grained scalation)

This document address some of the challenges that arise when using an architecture based on microservices:

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

### 3.3 Eureka

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