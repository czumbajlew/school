# DRAFT
# School Management

## What is it?

Application for managing school from director point of view also some functionalities available for school employees and students.

## Technologies
As build tool maven is used

### Application:
- Spring Boot Web ver. 3.0.0
- Spring Data JPA
- PostgreSQL

### Tests:
- TestContainers
- JUnit
- Mockito

## How to start
After download project build docker image in dev folder:
`docker-compose up -d`

When container is build and ready then run application with *dev* profile:
- `mvn clean package -P dev`
- `mvn spring-boot:run -P dev`
