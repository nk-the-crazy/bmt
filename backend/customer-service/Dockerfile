FROM openjdk:17-alpine

WORKDIR /app

COPY ./target/customer-service-1.0.0.jar /app/customer-service.jar

ENTRYPOINT ["java", "-jar","customer-service.jar"]
EXPOSE 8184