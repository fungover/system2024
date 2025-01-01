FROM maven:3-eclipse-temurin-23 AS builder
COPY ./ /app
RUN mvn --no-transfer-progress --file /app/pom.xml clean package -DskipTests
WORKDIR /application
ARG JAR_FILE=/app/target/*.jar
RUN cp ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM eclipse-temurin:23.0.1-jre
EXPOSE 8080
WORKDIR /application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
