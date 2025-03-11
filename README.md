# Recipe Management Application

## Project Overview
This is a standalone Java-based RESTful application that allows users to manage their favorite recipes. Users can add, update, delete, and fetch recipes, as well as filter them based on various criteria like ingredients, vegetarian options, and instructions.

## Features
- User authentication using JWT-based security
- CRUD operations for managing recipes
- Filtering options based on:
  - Vegetarian or non-vegetarian
  - Number of servings
  - Inclusion/exclusion of specific ingredients
  - Text search within cooking instructions
- API documentation using Swagger
- Data persistence with MongoDB
- Unit and integration tests (TBD)

## Tech Stack
- Backend: Java 17, Spring Boot, Spring Security, JWT
- Database: MongoDB
- Build Tool: Maven
- Testing: JUnit 5, Mockito (TBD)

## Installation & Setup

### Prerequisites
- Java 17 or later installed
- Maven installed
- MongoDB running locally or remotely

## Clone the Repository
```sh
git clone <repository_url>
cd recipe-management
```

## Setup Environment Variables
Modify `src/main/resources/application.properties` to match your MongoDB connection:
```
spring.data.mongodb.uri=mongodb://localhost:27017/recipe_db
spring.data.mongodb.database=recipe_db
```

## Run the Application
```sh
mvn spring-boot:run
```

The application will start at `http://localhost:8080`.

## API Documentation
Once the application is running, access the Swagger API documentation at:

http://localhost:8080/swagger-ui.html


## Running Tests
```sh
mvn test
```
(TBD: Include unit and integration tests)

## Deployment Guide
To create a runnable JAR file:
```sh
mvn clean package
java -jar target/recipe-management-1.0.0.jar
```

## Future Enhancements
- Implement missing unit and integration tests
- Add more robust exception handling
- Improve search filtering efficiency


### Contributors
Maintainer: Kurra Raviteja

