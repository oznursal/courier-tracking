# Courier Tracking
The Courier Tracking project aims to manage courier location changes.
Application built with Java 23 + Spring Boot 3.4.3 including CRUD operations and event streaming.

## Considerations
While implementing this application, I had the following considerations.

1. Hexagonal architecture will use for separation layer.
2. DDD pattern will be based on project.
3. Database for this project will be H2 DB. All database operations will be done with JPA interface for future-proofing.
4. Api will be based on rest architecture.
5. Clean Code: Code must be clean and well-designed. Any warnings should be solved or an appropriate reason must be given. Solid principles should be followed.
6. Exceptions will be handled with Spring Boot's RestControllerAdvice.
7. Logging will be handled with slf4j.
8. Input validations will be handled with jakarta bean validation.


## Features
This application provides the following key features:

* Courier, Store, GeoLocation, Entrance creation/viewing/editing/deletion

The project is implemented based on Java 23 and Spring Boot 3, utilizing various Spring technologies such as Spring MVC, Spring Data JPA. It uses H2 DB as the database and JUnit5 for writing test codes.

The core logic of the application is organized as follows:

* RestAdapter: Processes HTTP requests, calls business logic, and generates responses.
* Service: Implements business logic and interacts with the database through Repositories.
* Repository: An interface for interacting with the database, implemented using Spring Data JPA.

## Run application

### Build
#### With Dockerfile
    docker build -t courier-tracking:oznursal .

#### With Docker-Compose
    docker-compose build

### Run
#### With Dockerfile
    docker run -p 8080:8080 courier-tracking:oznursal .

#### With Docker-Compose
    docker-compose up

### To Create Courier 
    curl --request POST \
    --url http://localhost:8080/v1/couriers \
    --header 'Content-Type: application/json' \
    --data '{   
    "firstName": {firstName},
    "lastName" : {lastName},
    "email": {email},
    "phone": {phone}
    }'

### To Create Store
    curl --location 'http://localhost:8080/v1/stores' \
    --header 'Content-Type: application/json' \
    --data '{
    "name": {storeName},
    "lat": {latitude},
    "lng": {longitude}
    }'

### For postman usage
CollectionPath: [courier-tracking.postman_collection.json](courier-tracking.postman_collection.json)

### For swagger Usage 
    http://localhost:8080/swagger-ui/index.html in your browser.



