FROM maven:3.6-jdk-11 as maven_build

WORKDIR /app
COPY . /app
RUN mvn clean install -Dmaven.test.skip

FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY --from=maven_build app/target/backend-paypal-integration-1.0-SNAPSHOT.jar backend-paypal-integration-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","backend-paypal-integration-1.0-SNAPSHOT.jar"]
