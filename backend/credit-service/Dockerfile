FROM openjdk:17-alpine

WORKDIR /app

COPY ./target/credit-service-1.0.0.jar /app/credit-service.jar

ENTRYPOINT ["java", "-jar","credit-service.jar"]
EXPOSE 8183