# Using 21 version of JDK
FROM openjdk:21-jdk-slim

# Copying of jar-file
COPY target/server-0.0.1-SNAPSHOT.jar server.jar

# Opening of port
EXPOSE 8000

# Starting server
ENTRYPOINT ["java", "-jar", "/server.jar"]
