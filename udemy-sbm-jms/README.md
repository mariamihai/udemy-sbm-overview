# Udemy - Spring Boot Microservices - JMS Messaging project
## Description
The current project is part of the "Spring Boot Microservices with Spring Cloud" [Udemy course](https://www.udemy.com/course/spring-boot-microservices-with-spring-cloud-beginner-to-guru/). 

This is an initial project about JMS Messaging that will be expanded for the microservices that are constructed in the course.

An overview of all the projects involved can be found [here](../../..).

## Docker image
For the project, I am using an ActiveMQ Artemis image. 
Check the docker project [here](https://github.com/vromero/activemq-artemis-docker/blob/master/README.md).

Creating the container:
```
docker run -it --rm -p 8161:8161 -p 61616:61616 vromero/activemq-artemis
```

| Property | Value | 
| --------| -----|
| username | artemis |
| password | simetraehcapa | 

## Status
**[COMPLETED]** - As I finished the section of the course and the associated project, I am setting a personal status of "Completed" and will probably not update this repository in the near future as this was a learning project.
