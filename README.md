# Shalion Coding Exercise

A Java REST API built as part of the Shalion coding exercise. The application is fully containerized using Docker and backed by a PostgreSQL database.

## Tech Stack

- **Java** — Application language
- **Gradle** — Build tool
- **PostgreSQL** — Relational database
- **Docker / Docker Compose** — Containerization

## Prerequisites

Make sure you have the following installed:

- [Docker](https://www.docker.com/get-started) & Docker Compose
- [Java JDK 17+](https://adoptium.net/) (if running locally without Docker)

## Getting Started

### Run with Docker (recommended)

The easiest way to run the project is with Docker Compose, which will spin up both the API and the database:

```bash
docker-compose up --build
```

The API will be available at `http://localhost:8080`.

### Run locally

1. Make sure a PostgreSQL instance is running and accessible.

2. Build the project:
   ```bash
   ./gradlew build
   ```

3. Run the application:
   ```bash
   ./gradlew bootRun
   ```

## Environment Variables

| Variable      | Default                                    | Description              |
|---------------|--------------------------------------------|--------------------------|
| `DB_URL`      | `jdbc:postgresql://db:5432/mydb`           | JDBC connection URL      |
| `DB_USERNAME` | `postgres`                                 | Database username        |
| `DB_PASSWORD` | `postgres`                                 | Database password        |

> ⚠️ For production, make sure to override the default credentials with secure values.

## Database

The application uses **PostgreSQL 16**. When running via Docker Compose, the database is automatically configured with:

- **Database name:** `mydb`
- **Port:** `5432`

## Project Structure

```
├── src/                  # Application source code
├── gradle/               # Gradle wrapper files
├── Dockerfile            # Docker image definition
├── docker-compose.yml    # Docker Compose configuration
├── build.gradle.kts      # Gradle build script
└── settings.gradle.kts   # Gradle settings
```

## License

This project was created for evaluation purposes as part of a coding exercise for Shalion.