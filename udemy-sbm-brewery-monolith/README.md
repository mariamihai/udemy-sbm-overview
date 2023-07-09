[![CircleCI](https://circleci.com/gh/mariamihai/udemy-sbm-brewery-monolith.svg?style=shield)](https://circleci.com/gh/mariamihai/udemy-sbm-brewery-monolith)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=mariamihai_udemy-sbm-brewery-monolith&metric=alert_status)](https://sonarcloud.io/dashboard?id=mariamihai_udemy-sbm-brewery-monolith)

# Brewery Monolith


## Description
The current project is the monolith described under section "Deconstructing the Monolith" in the "Spring Boot Microservices with Spring Cloud" course.
This will be deconstructed under multiple microservices.

This is a brewery containing a tasting room. Different types of beers will be brewed and when the inventory is consumed, more will be created.

## API Version
Currently the application is at _v1_. All calls contain the version number in the URL.

## Brewery
Currently you can only check the existing breweries and investigate one of them via de API. 
The creation or update of a brewery is not possible as it is not relevant in the project.


### API calls

#### Obtain all breweries

 * __URI:__ _api/v1/brewery_

 * __Method:__ _GET_

 * __URL params:__ <br/>
    * required: - <br/>
    * optional: -
    
 * __Success response:__
    * Code: 200 <br/>
    * Content: 
    
       ``` 
       [
           {
               "id": "5aa94d63-5a55-4a2a-b1fc-df08823983c8",
               "version": 0,
               "createdDate": "2020-04-21T13:22:18.185+0000",
               "lastModifiedDate": "2020-04-21T13:22:18.185+0000",
               "breweryName": "Dummy Brewery 1",
               "new": false
           },
           {
               "id": "7dbb2bce-a7a2-4c78-962e-a83eae834a5a",
               "version": 0,
               "createdDate": "2020-04-21T13:22:18.239+0000",
               "lastModifiedDate": "2020-04-21T13:22:18.239+0000",
               "breweryName": "Dummy Brewery 2",
               "new": false
           }
       ]
        ```

 * __Error Response:__ -
 
 (Empty array is returned if no brewery exists but this is not considered by any means an error.)

    
#### Obtain brewery

 * __URI:__ _api/v1/brewery/:id_

 * __Method:__ _GET_

 * __URL params:__ <br/>
    * required: <br/>
        `id=[uuid]`
    * optional: -
    
 * __Success response:__
    * Code: 200 <br/>
    * Content: 
    ```
    {
        "id": "5aa94d63-5a55-4a2a-b1fc-df08823983c8",
        "version": 0,
        "createdDate": "2020-04-21T13:22:18.185+0000",
        "lastModifiedDate": "2020-04-21T13:22:18.185+0000",
        "breweryName": "Dummy Brewery 1",
        "new": false
    }
    ```

 * __Error Response:__ -
    * __Code:__ 500 INTERNAL SERVER ERROR <br/>
    * __Content:__ 
    ``` 
    {
        "timestamp": "2020-04-21T13:12:07.187+0000",
        "status": 500,
        "error": "Internal Server Error",
        "message": "No message available",
        "trace": "guru.springframework.brewery.monolith.web.controllers.NotFoundException [...]
        "path": "/api/v1/brewery/40ad19f3-4211-4105-96af-8aaae82a740c"
    }
    ```
    OR
    
    * __Code:__ 400 BAD REQUEST <br/>
    * __Content:__ 
    ```
    {
        "timestamp": "2020-04-21T13:25:20.119+0000",
        "status": 400,
        "error": "Bad Request",
        "message": "Failed to convert value of type 'java.lang.String' to required type 'java.util.UUID'; nested exception is java.lang.IllegalArgumentException: Invalid UUID string: 1",
        "trace": "org.springframework.web.method.annotation.MethodArgumentTypeMismatchException: [...]
        "path": "/api/v1/brewery/a"
     }
     ```
     
