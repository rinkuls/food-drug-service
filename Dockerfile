#################
# Build the  JAVA APP #
#################

FROM openjdk:17-jdk-alpine

WORKDIR /app

# Copy the application JAR file into the Docker image
COPY ./target/food-drug-service-0.0.1-SNAPSHOT.jar /app

# Expose port 8167 to access the application from outside the container
EXPOSE 8167

# Run the application
ENTRYPOINT ["java", "-jar", "food-drug-service-0.0.1-SNAPSHOT.jar"]
