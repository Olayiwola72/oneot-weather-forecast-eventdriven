# Stage 1: Maven Build
FROM maven:3.8.1-openjdk-17-slim AS builder

LABEL maintainer="Olayiwola Akinnagbe"

LABEL version="1.0"

LABEL description="Weather Forecast Search Microservice"

# Set the working directory in the container
WORKDIR /app

# Copy the Gradle project files
COPY gradlew .
COPY gradle/ gradle/
COPY settings.gradle .
COPY build.gradle .

# Copy the rest of the project files
COPY common-microservice/ common-microservice/
COPY search-microservice/ search-microservice/

# Load environment variables
COPY .env /app/.env

# Grant execution permissions
RUN chmod +x ./gradlew

# Clean and build the application
RUN ./gradlew clean :search-microservice:bootJar --warning-mode all

# Stage 2: Create the final image
FROM eclipse-temurin:17-jre-alpine

# Set the working directory in the final image
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/search-microservice/build/libs/search-microservice-0.0.1-SNAPSHOT.jar /app/search-microservice-0.0.1-SNAPSHOT.jar

# Load environment variables from the builder stage
COPY --from=builder /app/.env /app/.env

# Command to run the application
ENTRYPOINT ["java", "-jar", "search-microservice-0.0.1-SNAPSHOT.jar"]
