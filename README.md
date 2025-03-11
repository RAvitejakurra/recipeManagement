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
- Unit and integration tests

## Tech Stack
- Backend: Java 17, Spring Boot, Spring Security, JWT
- Database: MongoDB
- API Documentation: Swagger UI
- Build Tool: Maven

## Installation & Setup
1. Clone the repository:
   git clone https://github.com/your-repo/recipe-management.git
   
2. Ensure MongoDB is running:

   mongod --dbpath <your-db-path>

3. Build and run the project:

   mvn clean install
   mvn spring-boot:run

## API Endpoints
- `POST /recipes` - Create a new recipe
- `GET /recipes` - Retrieve all recipes
- `GET /recipes/{id}` - Retrieve a recipe by ID
- `PUT /recipes/{id}` - Update a recipe
- `DELETE /recipes/{id}` - Delete a recipe
- `GET /recipes/search?vegetarian=true&include=potatoes` - Search for recipes

## API Documentation
The application provides Swagger UI for API testing.
Access it at: [http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/)

## Running Tests
To run unit and integration tests, execute:

mvn test


