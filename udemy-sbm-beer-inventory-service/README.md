# SBM Beer Inventory Service
Spring Boot Microservice project

  - [Description](#description)
  - [API version](#api-version)
  - [Docker images](#docker-images)
  - [Implementation details](#implementation-details)
    - [Properties](#properties)
    - [Security](#security)
    - [Profiles](#profiles)
    - [Additional applications needed](#additional-applications-needed)
      - [MySQL](#mysql)
      - [JMS with ActiveMQ Artemis](#jms-with-activemq-artemis)
      - [Distributed Tracing with Spring Cloud Sleuth and Zipkin](#distributed-tracing-with-spring-cloud-sleuth-and-zipkin)
  - [API calls](#api-calls)
    - [Get existing inventory for a specific beer](#get-existing-inventory-for-a-specific-beer)
    - [Get all existing inventory](#get-all-existing-inventory)

## Description
The current project is part of the "Spring Boot Microservices with Spring Cloud" [Udemy course](https://www.udemy.com/course/spring-boot-microservices-with-spring-cloud-beginner-to-guru/). 

The project adds to the inventory the beers brewed by the [Beer Service](https://github.com/mariamihai/udemy-sbm-beer-service) 
and validates the allocation of inventory to an order created by the [Beer Order Service](https://github.com/mariamihai/udemy-sbm-beer-order-service) 
It removes the allocated inventory and inserts back inventory from cancelled orders.

An overview of all the projects involved can be found [here](../../..).

## API version
_V1_ is the current implementation. No changes to the project are expected to be made in the future that will affect 
the existing endpoints.

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
spring.application.name=beer-inventory-service
```

- application server port
```
server.port=8082
```

### Security
I am using Basic Auth to secure the current project. For better implementations of securing an application, check 
[this](https://github.com/mariamihai/spring-security-amigoscode-tutorial) project.
```
spring.security.user.name=inv
spring.security.user.password=invPass
```

### Profiles
Active profiles: `local`, `local-discovery`.

(The `localmysql` profile was used for the local MySQL connection when starting to develop the services and breaking 
the monolith, the `local` profile is obtained from the Config Service and it is used currently.)

### Additional applications needed
#### MySQL
When running locally, I am using a Docker container for the MySQL databases. Check the Docker Hub [MySQL page](https://hub.docker.com/_/mysql).

Creating the container:
```
docker run -p 3306:3306 --name beer-mysql -e MYSQL_ROOT_PASSWORD=root_pass -d mysql:8
```

The initial script for the project's database is under `src/main/scripts/mysql-init.sql` file.

| Property | Value | 
| --------| -----|
| database name | beersinventoryervice |
| port | 3306 (default) |
| username | beer_inventory_service |
| password | password | 

#### JMS with ActiveMQ Artemis
JMS is used for communication with the Beer Service and Beer Order Service.

Creating the container:
```
docker run -it --rm -p 8161:8161 -p 61616:61616 vromero/activemq-artemis
```

The queues related to the current project are:
- `new-inventory` - obtain update with the newly brewed beers from the Beer Service
- `allocate-order` - validates the allocation of an order received from the Beer Order Service
- `allocate-order-response` - sends the result of the allocation to the Beer Order Service
- `de-allocate-order` - receives a deallocation request from the Beer Order Service if the order was invalidated

| Property | Value | 
| --------| -----|
| username | artemis |
| password | simetraehcapa | 

#### Distributed Tracing with Spring Cloud Sleuth and Zipkin
Creating the Zipkin container:
```
docker run --name zipkin -p 9411:9411 openzipkin/zipkin
```

## API calls
### Get existing inventory for a specific beer
* __URI:__ _/api/v1/beer/:beerId/inventory_

 * __Method:__ _GET_

 * __URL params:__ <br/>
    * required: <br/>
        beerId=[uuid]
    * optional: -
    
 * __Success response:__
    * Code: 200 OK <br/>
    * Content:
    
       ``` 
       [
           {
               "id": "53c1ba86-aefa-41a3-aa3d-e8924e57e782",
               "createdDate": "2020-09-03T12:20:47.316Z",
               "lastModifiedDate": "2020-09-03T12:39:08.187Z",
               "beerId": "357d11d2-ab0d-4bff-bfc2-05601afd0c6e",
               "quantityOnHand": 115
           }
       ]
       ```

### Get all existing inventory
* __URI:__ _/api/v1/allinventory_

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
               "id": "53c1ba86-aefa-41a3-aa3d-e8924e57e782",
               "createdDate": "2020-09-03T12:20:47.316Z",
               "lastModifiedDate": "2020-09-03T12:37:56.185Z",
               "beerId": "357d11d2-ab0d-4bff-bfc2-05601afd0c6e",
               "quantityOnHand": 117
           },
           {
               "id": "7ca0a891-1051-46c6-b6e1-985a39f950b6",
               "createdDate": "2020-09-03T12:20:47.447Z",
               "lastModifiedDate": "2020-09-03T12:38:20.135Z",
               "beerId": "94d47759-9e0f-4ed5-bf75-caeda6206da5",
               "quantityOnHand": 122
           },
           {
               "id": "d580c531-fd5c-46f1-b206-6e4ffb8931f9",
               "createdDate": "2020-09-03T12:20:42.894Z",
               "lastModifiedDate": "2020-09-03T12:38:08.145Z",
               "beerId": "f5d5fa59-26f2-4c55-83df-e841c9fd9c22",
               "quantityOnHand": 114
           }
       ]
       ```