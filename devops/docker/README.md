![alt text](static/docker-logo.jpg "DOCKER-LOGO")

# Docker best practices

## Index

* [Docker for Developers](#docker-for-developers)
  * [Docker-Compose](#docker-compose)
  * [Docker for testing](#docker-for-testing)
  * [Writing dockerfiles](#writing-dockerfiles)
* [Security](#security)
* [Cloud Infrastructure](#cloud-infrastructure)
  * [ECS](#ecs)
* [Tips & Tricks](#tips-and-tricks)
* [References](#references)

### Docker for Developers

#### Docker-Compose
Docker-compose is a tool that allows to define multiple docker-containers using a configuration file and run them with a single command.

This is an example of a docker-compose.yml configuration file:
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

You can run the containers with the command (by default containers are recreated if already exist)

````
docker-compose up
````

Docker compose generates an isolated enviroment when launching based on the name of the project directory. You can set a custom project name by using the -p command line option or the COMPOSE_PROJECT_NAME environment variable

````
docker-compose -p myproject up
````

You can also specify the number of containers to run for a service. In the following example three containers will be used to run myservice
````
docker-compose scale myservice=3
````

### Writing dockerfiles

Images can be built automatically following the instructions defined in a Dockerfile. These are general guidelines for writing dockerfiles:

* Keep in mind that the docker containers produced by the images defined in your Dockerfile should be ephemeral. So they can be stopped, destroyed or rebuilt with minimal setup and configuration.

* Use a .dockerignore file to specify excluded files and directories to increase the build performance

* Keep the image as minimal as posible. Do not install unnecesary packages. Use small base images

* One proccess per container. Split aplications into multiple containers and use container linking if needed

* Minimize the number of layers, but also keep in mind maintainability and readability of Dockerfiles, so you need to find a balance

* Sort multiline arguments alphanumerically to avoid duplication of packages and to improve readability


### Security

You can use the following tools to check security issues on docker images and containers:
 * [Docker bench for security](#https://github.com/docker/docker-bench-security) It is a Script to check potential vulerabilities and best practices on docker deployments based on the [CIS Docker 1.6 benchmark document](#https://benchmarks.cisecurity.org/tools2/docker/CIS_Docker_1.6_Benchmark_v1.0.0.pdf)


### References

* https://docs.docker.com/compose/
* https://docs.docker.com/engine/articles/dockerfile_best-practices/
* [Link](http://www.url.to) Description

___

[BEEVA](http://www.beeva.com) | 2015
