FROM eclipse-temurin:17-jdk-focal

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src

CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.jvmArguments=\"-Xdebug -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005\""]