# Stage 1: Build
FROM gradle:8.10.1-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle bootJar

# Stage 2: Run
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/build/libs/auth-server.jar app.jar
CMD ["java", "-jar", "app.jar"]
