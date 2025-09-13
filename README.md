# Biz Social Media

A Spring Boot application for business social media management with PostgreSQL integration.

## Features

- **RESTful API** for managing business users
- **PostgreSQL Database** integration with JPA/Hibernate
- **Test Containers** for integration testing
- **Maven** build system
- **Input Validation** with Bean Validation
- **Full CRUD Operations** with search functionality

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- Docker (for running tests with test containers)
- PostgreSQL (for running the application locally)

## API Endpoints

### Business Users Management

- `GET /api/business-users` - Get all business users
- `GET /api/business-users/{id}` - Get business user by ID  
- `POST /api/business-users` - Create new business user
- `PUT /api/business-users/{id}` - Update business user
- `DELETE /api/business-users/{id}` - Delete business user
- `GET /api/business-users/search?companyName=X` - Search by company name

### Request/Response Examples

**Create Business User:**
```json
POST /api/business-users
{
  "companyName": "Tech Corp",
  "email": "contact@techcorp.com",
  "description": "Leading technology company"
}
```

**Response:**
```json
{
  "id": 1,
  "companyName": "Tech Corp",
  "email": "contact@techcorp.com",
  "description": "Leading technology company",
  "createdAt": "2023-12-13T10:30:00",
  "updatedAt": "2023-12-13T10:30:00"
}
```

## Building and Running

### Build the application
```bash
mvn clean compile
```

### Run tests (with test containers)
```bash
mvn test
```

### Run the application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## Database Configuration

### PostgreSQL Setup

1. Install PostgreSQL locally or use Docker:
```bash
docker run --name postgres-biz -e POSTGRES_DB=bizdb -e POSTGRES_USER=bizuser -e POSTGRES_PASSWORD=bizpass -p 5432:5432 -d postgres:15-alpine
```

2. Update `src/main/resources/application.properties` if needed

### Test Environment

Tests use Test Containers to automatically start a PostgreSQL container during test execution. No manual database setup required for testing.

## Project Structure

```
src/
├── main/
│   ├── java/com/bizsocialmedia/
│   │   ├── BizSocialMediaApplication.java    # Main application class
│   │   ├── entity/BusinessUser.java          # JPA entity
│   │   ├── repository/BusinessUserRepository.java # Data access layer
│   │   └── controller/BusinessUserController.java # REST controller
│   └── resources/
│       └── application.properties            # Application configuration
└── test/
    ├── java/com/bizsocialmedia/
    │   ├── config/AbstractIntegrationTest.java    # Test base class
    │   ├── repository/BusinessUserRepositoryTest.java # Repository tests
    │   └── controller/BusinessUserControllerTest.java # Controller tests
    └── resources/
        └── application-test.properties       # Test configuration
```

## Technologies Used

- **Spring Boot 3.2.0** - Application framework
- **Spring Data JPA** - Data access layer
- **PostgreSQL** - Database
- **Test Containers** - Integration testing
- **Maven** - Build tool
- **Hibernate** - ORM
- **Bean Validation** - Input validation
- **JUnit 5** - Testing framework
