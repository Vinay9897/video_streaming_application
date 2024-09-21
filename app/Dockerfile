# Use a base image with Java runtime
FROM openjdk:17-jre-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file directly (assuming it’s built already)
COPY target/*.jar app.jar

# Expose the application port (default is 8080)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
