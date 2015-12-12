![alt text](static/docker-logo.jpg "DOCKER-LOGO")

# Docker best practices

## Index

* [Docker for Developers](#docker-for-developers)
  * [Docker-Compose](#docker-compose)
  * [Docker for testing](#docker-for-testing)
  * [Image customization](#image-customization)
* [Cloud Infrastructure](#cloud-infrastructure)
  * [ECS](#ecs)
* [Tips & Tricks](#tips-and-tricks)
* [References](#references)

### Docker for Developers

#### Docker-Compose
Docker-compose is a tool that allows to define multiple docker-containers using a configuration file and run them with a single command.

This is an example of a docker-compose configuration file:
````yaml
mongodb:
  image: mongodb
  command: '--smallfiles'
  ports:
    - "27017:27017"

zookeeper:
  image: zookeeper
  ports:
    - "2181:2181"
  environment:
    ZK_DATADIR: '/tmp/zk/data'

kafka:
  image: kafka
  ports:
    - "9092:9092"
  links:
    - zookeeper:zk
  environment:
    KAFKA_CREATE_TOPICS: "topic1:1:1,topic2:1:1"
    KAFKA_ADVERTISED_HOST_NAME: 172.17.42.1
  volumes:
    - /var/run/docker.sock:/var/run/docker.sock

````



### Image customization



### References

* [Link](http://www.url.to) Description
* [oficialsite.org](http://www.oficialwebsite.org) API & Docs
* [Overapi Cheatsheet](http://overapi.com/example/) Cheatsheet

___

[BEEVA](http://www.beeva.com) | 2015
