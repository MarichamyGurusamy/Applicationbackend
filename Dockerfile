# Use official OpenJDK 21 runtime image
FROM openjdk:21-jdk-alpine

# Create a temp volume (optional)
VOLUME /tmp

# Copy the built jar into the container
COPY target/yourapp.jar app.jar

# Expose the port your app listens on (adjust if needed, default Spring Boot is 8080)
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app.jar"]
