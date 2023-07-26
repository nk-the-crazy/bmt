FROM openjdk:17-alpine

WORKDIR /app

COPY ./target/messaging-service-1.0.0.jar /app/messaging-service.jar

ENTRYPOINT ["java", "-jar","messaging-service.jar"]
EXPOSE 8182