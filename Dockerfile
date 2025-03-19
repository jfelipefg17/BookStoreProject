# Etapa de construcción
FROM maven:3-eclipse-temurin-17 AS build
WORKDIR /app
COPY projectBookStore /app
WORKDIR /app
ENV LANG C.UTF-8
RUN mvn clean package -DskipTests


# Etapa de ejecución
FROM eclipse-temurin:17-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
