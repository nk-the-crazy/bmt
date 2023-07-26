FROM openjdk:17-alpine

WORKDIR /app

COPY ./target/identity-service-1.0.0.jar /app/identity-service.jar

ENTRYPOINT ["java", "-jar","identity-service.jar"]
EXPOSE 9000