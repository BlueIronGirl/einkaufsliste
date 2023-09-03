FROM openjdk:17-slim-buster as build

COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
RUN ./mvnw -B dependency:go-offline
COPY src src
RUN ./mvnw -B package -Dmaven.test.skip=true

FROM openjdk:17-jdk-slim
COPY --from=build target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]