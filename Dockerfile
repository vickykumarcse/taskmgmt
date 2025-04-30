# Stage 1: Build the application using Maven Wrapper and Java 21
FROM eclipse-temurin:21-jdk AS build
WORKDIR /taskmgmt

# Copy everything (including mvnw and .mvn folder)
COPY . .

# Make wrapper executable
RUN chmod +x mvnw

# Build the app (skip tests for faster build)
RUN ./mvnw clean package -DskipTests

# Stage 2: Create a lightweight runtime image with just Java and the JAR
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /taskmgmt

# Copy the built JAR from the previous stage
COPY --from=build /taskmgmt/target/*.jar taskmgmt.jar

# Expose Spring Boot port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "taskmgmt.jar"]
