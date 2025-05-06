# Produtos API

This is a Spring Boot application for managing products and users. It uses Java 17, Spring Boot, MySQL, and Docker.

## Java Installation

### Requirements
- Java 17 JDK

### Installation Steps

#### Windows
1. Download the Java 17 JDK from [Oracle](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or [OpenJDK](https://adoptium.net/)
2. Run the installer and follow the installation wizard
3. Set the JAVA_HOME environment variable:
   - Right-click on 'This PC' and select 'Properties'
   - Click on 'Advanced system settings'
   - Click on 'Environment Variables'
   - Under 'System variables', click 'New'
   - Set Variable name as 'JAVA_HOME'
   - Set Variable value as the JDK installation path (e.g., C:\Program Files\Java\jdk-17)
4. Add Java to the PATH:
   - In the same 'Environment Variables' dialog, find the 'Path' variable under 'System variables'
   - Click 'Edit'
   - Click 'New' and add '%JAVA_HOME%\bin'
5. Verify the installation by opening a command prompt and typing:
   ```
   java -version
   ```

#### macOS
1. Install Homebrew if not already installed:
   ```
   /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
   ```
2. Install OpenJDK 17:
   ```
   brew install openjdk@17
   ```
3. Add Java to your PATH:
   ```
   echo 'export PATH="/usr/local/opt/openjdk@17/bin:$PATH"' >> ~/.zshrc
   ```
   or if using bash:
   ```
   echo 'export PATH="/usr/local/opt/openjdk@17/bin:$PATH"' >> ~/.bash_profile
   ```
4. Verify the installation:
   ```
   java -version
   ```

#### Linux (Ubuntu/Debian)
1. Update package list:
   ```
   sudo apt update
   ```
2. Install OpenJDK 17:
   ```
   sudo apt install openjdk-17-jdk
   ```
3. Verify the installation:
   ```
   java -version
   ```

## Docker Installation and Setup

### Requirements
- Docker
- Docker Compose

### Installation Steps

#### Windows
1. Download and install [Docker Desktop for Windows](https://www.docker.com/products/docker-desktop)
2. Follow the installation wizard
3. Start Docker Desktop

#### macOS
1. Download and install [Docker Desktop for Mac](https://www.docker.com/products/docker-desktop)
2. Follow the installation wizard
3. Start Docker Desktop

#### Linux (Ubuntu/Debian)
1. Update package list:
   ```
   sudo apt update
   ```
2. Install Docker:
   ```
   sudo apt install docker.io
   ```
3. Install Docker Compose:
   ```
   sudo apt install docker-compose
   ```
4. Start and enable Docker:
   ```
   sudo systemctl start docker
   sudo systemctl enable docker
   ```
5. Add your user to the docker group to run Docker without sudo:
   ```
   sudo usermod -aG docker $USER
   ```
   (Log out and log back in for this to take effect)

### Running with Docker

The project includes a `docker-compose.yml` file that sets up both the application and a MySQL database. To start the entire application:

1. Navigate to the project root directory
2. Run:
   ```
   docker-compose up -d
   ```

This will:
- Build and start the Spring Boot application container
- Start a MySQL 8.0 container with the following configuration:
  - Container name: mysql-db
  - Port: 3306 (mapped to 3306 inside the container)
  - Username: root
  - Password: cleanarch_pass
  - Database: produto_db

The application will be available at http://localhost:8080:
- Swagger UI: http://localhost:8080/swagger-ui.html
- API docs: http://localhost:8080/v3/api-docs

To stop all services:
```
docker-compose down
```

To stop all services and remove all data:
```
docker-compose down -v
```

To view logs from the application:
```
docker logs spring-app -f
```

## Running the Project

### Using Visual Studio Code

1. Install the following extensions:
   - Extension Pack for Java
   - Spring Boot Extension Pack

2. Open the project folder in VS Code

3. Configure Java 17:
   - Press `Ctrl+Shift+P` (or `Cmd+Shift+P` on macOS)
   - Type "Java: Configure Java Runtime"
   - Select Java 17 for your project

4. Start the database using Docker (if not running the full application with Docker):
   ```
   docker-compose up -d mysql
   ```

5. Run the application:
   - Open the Spring Boot Dashboard (from the left sidebar)
   - Click the play button next to the application name

6. The application will be available at http://localhost:8080
   - Swagger UI: http://localhost:8080/swagger-ui.html
   - API docs: http://localhost:8080/v3/api-docs

### Using IntelliJ IDEA

1. Open IntelliJ IDEA

2. Open the project:
   - Select "Open" or "Import Project"
   - Navigate to the project directory and select it

3. Configure Java 17:
   - Go to File > Project Structure
   - Under Project Settings > Project, set the Project SDK to Java 17
   - Set the Project language level to 17

4. Start the database using Docker (if not running the full application with Docker):
   ```
   docker-compose up -d mysql
   ```

5. Run the application:
   - Navigate to the main class (`ProdutosApplication.java`)
   - Right-click and select "Run 'ProdutosApplication'"
   - Alternatively, click the green play button in the top-right corner

6. The application will be available at http://localhost:8080
   - Swagger UI: http://localhost:8080/swagger-ui.html
   - API docs: http://localhost:8080/v3/api-docs

## Project Structure

- `src/main/java/com/desafio/produtos`: Main source code
  - `controllers`: REST controllers
  - `dominio`: Domain entities
  - `dto`: Data Transfer Objects
  - `exceptions`: Custom exceptions
  - `repositorios`: JPA repositories
  - `servicos`: Business logic services
  - `validation`: Custom validation annotations

- `src/main/resources`: Configuration files
  - `application.properties`: Application configuration
  - `db/migration`: Flyway database migration scripts

## Database Migrations

The project uses Flyway for database migrations. Migration scripts are located in `src/main/resources/db/migration`.

When the application starts, Flyway automatically applies any pending migrations to the database.
