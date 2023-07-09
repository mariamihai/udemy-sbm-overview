# SBM Beer Service
Spring Boot Microservice project.

  - [Description](#description)
  - [API version](#api-version)
  - [Docker images](#docker-images)
  - [Implementation details](#implementation-details)
    - [Properties](#properties)
    - [Environment variables for running locally](#environment-variables-for-running-locally)
    - [Profiles](#profiles)
    - [Additional applications needed](#additional-applications-needed)
      - [MySQL](#mysql)
      - [JMS with ActiveMQ Artemis](#jms-with-activemq-artemis)
      - [Distributed Tracing with Spring Cloud Sleuth and Zipkin](#distributed-tracing-with-spring-cloud-sleuth-and-zipkin)
  - [API calls](#api-calls)
    - [Obtain all beers](#obtain-all-beers)
    - [Obtain beer by id](#obtain-beer-by-id)
    - [Obtain beer by upc](#obtain-beer-by-upc)
    - [Save new beer](#save-new-beer)
    - [Update beer](#update-beer)

## Description
The current project is part of the "Spring Boot Microservices with Spring Cloud" [Udemy course](https://www.udemy.com/course/spring-boot-microservices-with-spring-cloud-beginner-to-guru/). 

The project constantly verifies that the [Beer Order Service](https://github.com/mariamihai/udemy-sbm-beer-order-service) 
didn't eliminate all the existing inventory for a particular beer and brews more for the 
[Beer Inventory Service](https://github.com/mariamihai/udemy-sbm-beer-inventory-service) if needed.
Different types of beer can be added and verified.

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
spring.application.name=beer-service
```
- application server port
```
server.port=8080
```
- checking the inventory is done every 10 s under `BrewingService.checkForLowInventory()` method

### Environment variables for running locally
**sbm.brewery.beer-inventory-service-host** contained originally the beer inventory service host. 

`INVENTORY_SERVICE_HOST` was added to be used both for Docker and when running locally to set the host. For local use, 
the value should be `http://localhost:8082`. For creating a Docker container, the value is set in the docker-compose file.

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
| database name | beerservice |
| port | 3306 (default) |
| username | beer_service |
| password | password | 

#### JMS with ActiveMQ Artemis
JMS is used for communication with the Beer Inventory Service and Beer Order Service.

Creating the container:
```
docker run -it --rm -p 8161:8161 -p 61616:61616 vromero/activemq-artemis
```

The queues related to the current project are:
- `brewing-request` - send a brew request to make more inventory
- `new-inventory` - send update with the newly brewed beers to the Beer Inventory Service
- `validate-order` - receive request for validating an order from the Beer Order Service
- `validate-order-result` - sending the result back to the Beer Order Service

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
### Obtain all beers
 * __URI:__ _/api/v1/beer/_

 * __Method:__ _GET_

 * __URL params:__ <br/>
    * required: - <br/>
    * optional: -
        
  * __Query params:__ <br/>
     * required: - <br/>
     * optional: <br/>
         pageNumber=[int] <br/>
         pageSize=[int] <br/>
         beerName=[String] <br/>
         beerStyle=[BeerStyleEnum] <br/>
         showInventoryOnHand=[boolean]
    
 * __Success response:__
    * Code: 200 OK <br/>
    * Content:
       ``` 
       {
           "content": [
               {
                   "id": "357d11d2-ab0d-4bff-bfc2-05601afd0c6e",
                   "version": 0,
                   "createdDate": "2020-09-03T12:20:27+0000",
                   "lastModifiedDate": "2020-09-03T12:20:27+0000",
                   "beerName": "Galaxy Cat",
                   "beerStyle": "PALE_ALE",
                   "upc": "0631234300019",
                   "price": "12.40",
                   "quantityOnHand": null
               },
               {
                   "id": "94d47759-9e0f-4ed5-bf75-caeda6206da5",
                   "version": 0,
                   "createdDate": "2020-09-03T12:20:27+0000",
                   "lastModifiedDate": "2020-09-03T12:20:27+0000",
                   "beerName": "Pinball Porter",
                   "beerStyle": "PALE_ALE",
                   "upc": "0083783375213",
                   "price": "11.25",
                   "quantityOnHand": null
               },
               {
                   "id": "f5d5fa59-26f2-4c55-83df-e841c9fd9c22",
                   "version": 0,
                   "createdDate": "2020-09-03T12:20:27+0000",
                   "lastModifiedDate": "2020-09-03T12:20:27+0000",
                   "beerName": "Mango Bobs",
                   "beerStyle": "IPA",
                   "upc": "0631234200036",
                   "price": "12.95",
                   "quantityOnHand": null
               }
           ],
           "number": 0,
           "size": 25,
           "totalElements": 3,
           "pageable": {
               "sort": {
                   "sorted": false,
                   "unsorted": true,
                   "empty": true
               },
               "pageNumber": 0,
               "pageSize": 25,
               "offset": 0,
               "paged": true,
               "unpaged": false
           },
           "last": true,
           "totalPages": 1,
           "sort": {
               "sorted": false,
               "unsorted": true,
               "empty": true
           },
           "first": true,
           "numberOfElements": 3,
           "empty": false
       }
       ```
 
 `pageNumber` defaults to `0`.
 
 `pageSize` defaults to `25`.

 `showInventoryOnHand` defaults to `false`.
    
### Obtain beer by id
 * __URI:__ _/api/v1/beer/:beerId/_

 * __Method:__ _GET_

 * __URL params:__ <br/>
    * required: <br/>
        upc=[String] <br/>
    * optional: -
        
  * __Query params:__ <br/>
     * required: -
     * optional: <br/>
         showInventoryOnHand=[boolean]
    
 * __Success response:__
    * Code: 200 OK <br/>
    * Content:
       ``` 
       {
           "id": "357d11d2-ab0d-4bff-bfc2-05601afd0c6e",
           "version": 0,
           "createdDate": "2020-09-03T12:20:27+0000",
           "lastModifiedDate": "2020-09-03T12:20:27+0000",
           "beerName": "Galaxy Cat",
           "beerStyle": "PALE_ALE",
           "upc": "0631234300019",
           "price": "12.40",
           "quantityOnHand": null
       }
       ```
       
 `showInventoryOnHand` defaults to `false`.
    
### Obtain beer by upc
 * __URI:__ _/api/v1/beer/beerUpc/:upc/_

 * __Method:__ _GET_

 * __URL params:__ <br/>
    * required: <br/>
        upc=[String] <br/>
    * optional: -
        
  * __Query params:__ <br/>
     * required: -
     * optional: <br/>
         showInventoryOnHand=[boolean]
            
 * __Success response:__
    * Code: 200 OK <br/>
    * Content:
       ``` 
       {
           "id": "357d11d2-ab0d-4bff-bfc2-05601afd0c6e",
           "version": 0,
           "createdDate": "2020-09-03T12:20:27+0000",
           "lastModifiedDate": "2020-09-03T12:20:27+0000",
           "beerName": "Galaxy Cat",
           "beerStyle": "PALE_ALE",
           "upc": "0631234300019",
           "price": "12.40",
           "quantityOnHand": null
       }
       ```
       
 `showInventoryOnHand` defaults to `false`.
    
### Save new beer
 * __URI:__ _/api/v1/beer/_

 * __Method:__ _POST_

 * __URL params:__ <br/>
    * required: - <br/>
    * optional: - <br/>

 * __Successful call:__
     * __Data params:__ <br/>
        * required: <br/>
            BeerDto=[beerDto]
            ``` 
           {
               "beerName": "Galaxy Cat 2",
               "beerStyle": "PALE_ALE",
               "upc": "0331234300019",
               "price": "12.40"
           }
            ```
        * optional: - <br/>
            
     * __Response:__
        * Code: 201 CREATED <br/>
        * Content:
           ``` 
           {
               "id": "d42ad274-02e7-432f-bb3a-310ed0fe1868",
               "version": 0,
               "createdDate": "2020-09-03T12:57:26+0000",
               "lastModifiedDate": "2020-09-03T12:57:26+0000",
               "beerName": "Galaxy Cat 2",
               "beerStyle": "PALE_ALE",
               "upc": "0331234300019",
               "price": "12.40",
               "quantityOnHand": 0
           }
           ```
  
 * __Failed call:__
      * __Data params:__ <br/>
         * required: <br/>
             BeerDto=[beerDto] 
             ``` 
            {
                "version": 5,
                "beerName": "Galaxy Cat 2",
                "beerStyle": "PALE_ALE",
                "upc": "0331234300019",
                "price": "12.40"
            }
             ```
         * optional: - <br/>
     
     * __Response:__
        * __Code:__ 400 BAD REQUEST <br/>
        * __Content:__ 
        ``` 
        {
            "timestamp": "2020-09-03T12:49:34.791+00:00",
            "status": 400,
            "error": "Bad Request",
            "message": "",
            "path": "/api/v1/beer/"
        }
        ```
           
 The `id`, `version`, `createdDate` and `lastModifiedDate` have to be null.
  
 The `beerName` is valid when not blank.
  
 `BeerStyle`, `upc` and `price` are valid when not null.     
     
### Update beer
 * __URI:__ _/api/v1/beer/:beerId_

 * __Method:__ _PUT_

 * __URL params:__ <br/>
    * required: <br/>
        beerId=[uuid] <br/>
    * optional: - <br/>
       
 * __Successful call:__
     * __Data params:__ <br/>
         * required: <br/>
             BeerDto=[beerDto]
             ``` 
             {
                 "beerName": "Galaxy Cat 2",
                 "beerStyle": "PALE_ALE",
                 "upc": "0331234300019",
                 "price": "12.50",
                 "quantityOnHand": 0
             }
             ```
         * optional: - <br/>
             
     * __Response:__
         * Code: 204 NO CONTENT
         
 * __Failed call:__
    * __Data params:__ <br/>
        * required: <br/>
            BeerDto=[beerDto]
            ```
             {
                 "id": "d42ad274-02e7-432f-bb3a-310ed0fe1868",
                 "version": 0,
                 "createdDate": "2020-09-03T12:57:26+0000",
                 "lastModifiedDate": "2020-09-03T12:57:26+0000",
                 "beerName": "Galaxy Cat 2",
                 "beerStyle": "PALE_ALE",
                 "upc": "0331234300019",
                 "price": "12.40",
                 "quantityOnHand": 0
             }
            ```
        * optional: - <br/>
              
    * __Response:__
       * __Code:__ 400 BAD REQUEST <br/>
       * __Content:__
       ```
        {
            "timestamp": "2020-09-03T12:59:53.233+00:00",
            "status": 400,
            "error": "Bad Request",
            "message": "",
            "path": "/api/v1/beer/d42ad274-02e7-432f-bb3a-310ed0fe1868"
        }
       ```
           
 The `id`, `version`, `createdDate` and `lastModifiedDate` have to be null.
  
 The `beerName` is valid when not blank.
  
 `BeerStyle`, `upc` and `price` are valid when not null.     