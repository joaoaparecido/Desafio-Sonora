# User Management System

This project is a Java-based web application for user management with CPF validation.

## Prerequisites

- **Java**: JDK 21 or later
  - You can download it from [Oracle](https://www.oracle.com/java/technologies/downloads/#java21) or use [SDKMAN](https://sdkman.io/)
  - To verify your Java version:
    ```bash
    java -version
    ```

- **Docker**: Latest version
  - Required for running the database and other services
  - Install from [Docker's official website](https://www.docker.com/products/docker-desktop/)
  - Verify installation:
    ```bash
    docker --version
    docker compose --version
    ```

- **Maven**: Latest version (optional - project includes Maven wrapper)
  - If you prefer to use your local Maven installation:
    ```bash
    mvn --version
    ```

## Getting Started

1. **Clone the repository**
   ```bash
   git clone [repository-url]
   cd [project-directory]
   ```

2. **Start the database and required services**
   ```bash
   docker compose up -d
   ```
   This will start all necessary services defined in the `docker-compose.yml` file.

3. **Build the project**
   
   Using Maven wrapper:
   ```bash
   ./mvnw clean install
   ```
   Or with local Maven:
   ```bash
   mvn clean install
   ```

4. **Run the application**
   
   Using Maven wrapper:
   ```bash
   ./mvnw spring-boot:run
   ```
   Or with local Maven:
   ```bash
   mvn spring-boot:run
   ```

   The application will start on `http://localhost:8080`

## Database Configuration

The database connection is pre-configured in the docker-compose file. Default settings:
- Database: PostgreSQL
- Host: localhost
- Port: 5432
- Database name: [your-db-name]
- Username: [your-username]
- Password: [your-password]

These settings can be modified in the `docker-compose.yml` file.

## API Documentation

Once the application is running, you can access the API documentation at:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Development

### IDE Setup
- Recommended IDE: IntelliJ IDEA
- Ensure you hav