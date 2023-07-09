[![Docker](https://img.shields.io/docker/v/mariamihai/sbm-beer-inventory-failover?sort=date)](https://hub.docker.com/repository/docker/mariamihai/sbm-beer-inventory-failover)

# SBM Beer Inventory Failover Service
Spring Boot Microservice project.

  - [Description](#description)
  - [API version](#api-version)
  - [Docker images](#docker-images)
  - [Implementation details](#implementation-details)
    - [Properties](#properties)
    - [Profiles](#profiles)
  - [API call](#api-call)
    - [Failover endpoint](#failover-endpoint)

## Description
The current project is part of the "Spring Boot Microservices with Spring Cloud" [Udemy course](https://www.udemy.com/course/spring-boot-microservices-with-spring-cloud-beginner-to-guru/). 

It is a failover project for the [Beer Inventory Service](https://github.com/mariamihai/udemy-sbm-beer-inventory-service).
If the Beer  Inventory Service is down, the current project returns a "default" BeerInventoryDto object with beerId equal to `00000000-0000-0000-0000-00000000000` and quantityOnHand set to `999`.

An overview of all the projects involved can be found [here](https://github.com/mariamihai/udemy-sbm-overview).

## API version
Version was not added to the current project as the project contains only one endpoint with no parameters. No changes 
are expected to be made in the future to the project or to the existing endpoint.

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
spring.application.name=beer-inventory-failover
```
- application server port
```
server.port=8083
```

### Profiles
Active profile: `local-discovery`.

## API call
### Failover endpoint
* __URI:__ _/inventory-failover_

 * __Method:__ _GET_

 * __URL params:__ <br/>
    * required: - <br/>
    * optional: -
    
 * __Success response:__
    * Code: 200 OK <br/>
    * Content:
    
       ``` 
       [
           {
               "id": "40352018-6eca-4098-b358-6e8b96d54805",
               "createdDate": "2020-09-03T12:34:03.183487Z",
               "lastModifiedDate": "2020-09-03T12:34:03.18353Z",
               "beerId": "00000000-0000-0000-0000-000000000000",
               "quantityOnHand": 999
           }
       ]
       ```