# Spring Boot Microservices with Spring Cloud
Overview of the entire course and all projects from "Spring Boot Microservices with Spring Cloud" [Udemy course](https://www.udemy.com/course/spring-boot-microservices-with-spring-cloud-beginner-to-guru/). 


## 1. Main project
### 1.1. Description

### 1.2. The monolith

### 1.3. The microservices
#### 1.4. Local environment
##### 1.4.1. Additional Docker containers needed
- MySQL

I am using a Docker container for the MySQL databases. Check the Docker Hub [MySQL page](https://hub.docker.com/_/mysql).

Creating the container:
```
docker run -p 3306:3306 --name beer-mysql -e MYSQL_ROOT_PASSWORD=root_pass -d mysql:8
```
Stopping and restarting the container:
```
docker stop beer-mysql
docker start beer-mysql
```

- JMS with ActiveMQ Artemis
Check the Docker project [here](https://github.com/vromero/activemq-artemis-docker/blob/master/README.md).
```
docker run -it --rm -p 8161:8161 -p 61616:61616 vromero/activemq-artemis
```

- Zipkin
```
docker run --name zipkin -p 9411:9411 openzipkin/zipkin
```
##### 1.4.2. Developed microservices
- Eureka Service

Active profiles: -


- Gateway Service

Active profiles: local-discovery


- Config Service

Active profiles: -


- Beer Service

Active profiles: local, local-discovery


- Beer Order Service

Active profiles: local, local-discovery


- Beer Inventory Service

Active profiles: local, local-discovery


- Beer Inventory Failover Service

Active profiles: local-discovery


(The "localmysql" profile was used for the local MySQL connection when starting to develop the services and breaking the monolith, the "local" profile is obtained from the Config Service and used currently.)

##### 1.4.3. Default port mapping - for single host

| Service Name | Port | 
| --------| -----|
| [Eureka Service](https://github.com/mariamihai/udemy-sbm-brewery-eureka) | 8761 |
| [Gateway Service](https://github.com/mariamihai/udemy-sbm-brewery-gateway) | 9090 |
| [Config Service](https://github.com/mariamihai/udemy-sbm-config-server) | 8888 |
| [SBM Beer Service](https://github.com/mariamihai/udemy-sbm-beer-service) | 8080 |
| [SBM  Beer Order Service](https://github.com/mariamihai/udemy-sbm-beer-order-service) | 8081 |
| [SBM Beer Inventory Service](https://github.com/mariamihai/udemy-sbm-beer-inventory-service) | 8082 |
| [SBM Beer Inventory Failover Service](https://github.com/mariamihai/udemy-sbm-beer-inventory-failover) | 8083 |

#### 1.5. Docker based environment

## 2. Additional projects
