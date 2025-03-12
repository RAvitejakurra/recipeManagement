#Recipe Management Application

# Project Overview
This is a standalone Java-based RESTful application that allows users to manage their favorite recipes. Users can:
- Add, update, delete, and fetch recipes.
- Filter recipes based on:
  - Vegetarian or non-vegetarian.
  - Number of servings.
  - Inclusion/exclusion of specific ingredients.
  - Text search within cooking instructions.

# Features
- User authentication using JWT-based security.
- CRUD operations for managing recipes.
- Filtering options for ingredients, servings, and instructions.
- API documentation using Swagger.
- Data persistence with MongoDB.
- Unit and integration tests.

#  Tech Stack
- **Backend:** Java 17, Spring Boot, Spring Security, JWT.
- **Database:** MongoDB.
- **API Documentation:** Swagger UI.
- **Build Tool:** Maven.

# Installation & Setup

# Clone the Repository

git clone https://github.com/RAvitejakurra/recipeManagement.git


# Set Up MongoDB
Ensure MongoDB is installed and running. If not, follow these steps:

- **Run MongoDB locally:**

  mongod --dbpath <your-db-path>

- **OR Use Docker:**

  docker run -d -p 27017:27017 --name mongo-container mongo:latest


- **Verify MongoDB is running:**

  mongo


# Configure Environment Variables
Set up a `.env` file or modify `application.properties`:

spring.data.mongodb.uri=mongodb://localhost:27017/recipe-db
jwt.secret=your-secret-key
server.port=8080


# Running the Application

Run the application using Maven:

mvn spring-boot:run

Or build and run the JAR:

mvn clean install
java -jar target/recipe-management.jar


# API Endpoints

# Recipe Management
| Method | Endpoint | Description |
|--------|---------|-------------|
| `POST` | `/recipes` | Add a new recipe |
| `GET` | `/recipes` | Get all recipes |
| `GET` | `/recipes/{id}` | Get a recipe by ID |
| `DELETE` | `/recipes/{id}` | Delete a recipe by ID |

# Filtering Recipes
| Method | Endpoint | Description |
|--------|---------|-------------|
| `GET` | `/recipes/vegetarian` | Get vegetarian recipes |
| `GET` | `/recipes/servings?servings=4` | Get recipes for a specific number of servings |
| `GET` | `/recipes/include-ingredient?ingredient=potato` | Get recipes that include "potato" |
| `GET` | `/recipes/exclude-ingredient?ingredient=salmon` | Get recipes that **do NOT** contain "salmon" |
| `GET` | `/recipes/instructions?keyword=oven` | Get recipes that mention "oven" in instructions |

# Authentication (JWT)
| Method | Endpoint | Description |
|--------|---------|-------------|
| `POST` | `/auth/register` | Register a new user |
| `POST` | `/auth/login` | Authenticate and get a JWT token |

- **Use the token for protected endpoints:**

  Authorization: Bearer <your-token>


# API Documentation (Swagger UI)
Once the application is running, access API documentation at:

http://localhost:8080/swagger-ui/index.html




# Running Tests

### Unit Tests
Run all **unit tests** using:

mvn test


### **Integration Tests**
To run **API integration tests**, use:

mvn verify

# Next Steps & Improvements
- Implement more advanced search filters.
- Add more authentication roles (e.g., Admin).
- Improve front-end integration (React or Angular).
