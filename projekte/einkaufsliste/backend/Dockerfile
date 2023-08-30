FROM eclipse-temurin:17-jdk-focal

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src

CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.jvmArguments='-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005'"]