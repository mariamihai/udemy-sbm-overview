# SFG Brewery Eureka Service
Spring Boot Microservice project.

  - [Description](#description)
  - [Docker images](#docker-images)
  - [Implementation details](#implementation-details)
    - [Properties](#properties)
    - [Security](#security)
    - [Profiles](#profiles)

## Description
The current project is part of the "Spring Boot Microservices with Spring Cloud" [Udemy course](https://www.udemy.com/course/spring-boot-microservices-with-spring-cloud-beginner-to-guru/). 

The project provides service discovery and it is used by all other applications for registration and communication between themselves.

An overview of all the projects involved can be found [here](../../..).

## Docker images
Automatic building was not implemented for this project. The `latest` tag contains the best implementation considered 
appropriate to be used.

For automatic building of Docker images check the next projects:
- for [CircleCI](https://github.com/mariamihai/CIToDockerExampleProject)
- for [TravisCI](https://github.com/mariamihai/sma-overview) (all projects implemented under the "Spring Microservices in Action" book)

## Implementation details
### Properties
- the name of the application, used by the other services 
```
spring.application.name=eureka
```
- application server port
```
server.port=8761
```

### Security
I am using Basic Auth to secure the current project. For better implementations of securing an application, check 
[this](https://github.com/mariamihai/spring-security-amigoscode-tutorial) project.
```
spring.security.user.name=netflix
spring.security.user.password=eureka
```

### Profiles
There is no active profile defined for the current project.
