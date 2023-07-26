FROM openjdk:17-alpine

ADD ./target/api-gateway-1.0.0.jar api-gateway.jar

ENTRYPOINT ["java", "-jar","api-gateway.jar"]
EXPOSE 8080