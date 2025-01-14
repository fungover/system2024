FROM node:22.12.0-alpine AS frontend-builder
WORKDIR /frontend
COPY frontend/package*.json ./
RUN npm install
COPY frontend/ ./
RUN npm run build

FROM eclipse-temurin:21-jdk-alpine AS backend-builder
WORKDIR /app
COPY . .
COPY --from=frontend-builder /frontend/build ./src/main/resources/static
RUN ./mvnw clean package -DskipTests -Dfrontend.skip=true

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=backend-builder /app/target/*.jar app.jar
ENV SPRING_PROFILES_ACTIVE=prod
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"] 