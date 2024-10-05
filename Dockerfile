FROM openjdk:17-jdk-alpine
COPY ./target/*.jar api-parcial.jar
ENTRYPOINT ["java","-jar","api-parcial.jar"]