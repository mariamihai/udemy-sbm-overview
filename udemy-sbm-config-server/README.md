# SBM Cloud Config Service
Spring Boot Microservice project.

  - [Description](#description)
  - [Docker images](#docker-images)
  - [Implementation details](#implementation-details)
    - [Properties](#properties)
    - [Security](#security)
    - [Profiles](#profiles)
  - [API calls](#api-calls)
    - [Verify available properties](#verify-available-properties)
    - [Get the properties not attached to a profile](#get-the-properties-not-attached-to-a-profile)
    - [Get the properties for the local profile, and the properties not attached to a profile](#get-the-properties-for-the-local-profile-and-the-properties-not-attached-to-a-profile)
    
## Description
The current project is part of the "Spring Boot Microservices with Spring Cloud" [Udemy course](https://www.udemy.com/course/spring-boot-microservices-with-spring-cloud-beginner-to-guru/). 

The project provides externalized configuration for the principal services: [Beer Service](https://github.com/mariamihai/udemy-sbm-beer-service), 
[Beer Order Service](https://github.com/mariamihai/udemy-sbm-beer-order-service) and [Beer Inventory Service](https://github.com/mariamihai/udemy-sbm-beer-inventory-service).
It uses a [Git repository](https://github.com/mariamihai/udemy-sbm-brewery-config-repo) for the properties files.

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
spring.application.name=sfg-brewery-config
```
- application server port
```
server.port=8888
```

### Security
I am using Basic Auth to secure the current project. For better implementations of securing an application, check 
[this](https://github.com/mariamihai/spring-security-amigoscode-tutorial) project.
```
spring.cloud.config.username=MyUserName
spring.cloud.config.password=MyPassword
```

### Profiles
There is no active profile defined for the current project.

## API calls
### Verify available properties
 * __URI:__ _/application/profile_

 * __Method:__ _GET_

### Get the properties not attached to a profile
 * __URI:__ _/beer-service/default_

 * __Method:__ _GET_
    
### Get the properties for the local profile, and the properties not attached to a profile
 * __URI:__ _/beer-service/local_

 * __Method:__ _GET_