FROM openjdk:17-alpine

WORKDIR /app

COPY ./target/service-discovery-1.0.0.jar /app/service-discovery.jar

ENTRYPOINT ["java", "-jar","service-discovery.jar"]
EXPOSE 9000