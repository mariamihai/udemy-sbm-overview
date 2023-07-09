# SFG Brewery Gateway Service
Spring Boot Microservice project.

  - [Description](#description)
  - [Docker images](#docker-images)
  - [Implementation details](#implementation-details)
    - [Properties](#properties)
    - [Profiles](#profiles)

## Description
The current project is part of the "Spring Boot Microservices with Spring Cloud" [Udemy course](https://www.udemy.com/course/spring-boot-microservices-with-spring-cloud-beginner-to-guru/). 

The project represents the gateway service for the [Beer Service](https://github.com/mariamihai/udemy-sbm-beer-service),
[Beer Order Service](https://github.com/mariamihai/udemy-sbm-beer-order-service) and [Beer Inventory Service](https://github.com/mariamihai/udemy-sbm-beer-inventory-service)
projects.

An overview of all the projects involved can be found [here](../../..).

## Docker images
Automatic building was not implemented for this project. The `latest` tag contains the best implementation considered 
appropriate to be used.

For automatic building of Docker images check the next projects:
- for [CircleCI](https://github.com/mariamihai/CIToDockerExampleProject)
- for [TravisCI](https://github.com/mariamihai/sma-overview) (all projects implemented under the "Spring Microservices in Action" book)

## Implementation details
### Properties
- the name of the application, used by Eureka and the other services 
```
spring.application.name=sfg-brewery-gateway
```
- application server port
```
server.port=9090
```

### Profiles
Active profile: `local-discovery`.