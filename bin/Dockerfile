# Use an OpenJDK image to run the Spring Boot application
FROM openjdk:23-jdk-slim

# Copy the JAR file built by Maven or Gradle
COPY target/server-0.0.1-SNAPSHOT.jar server.jar

# Expose the application port
EXPOSE 5000

# Run the JAR file
ENTRYPOINT ["java", "-jar", "server.jar"]
