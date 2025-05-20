# Etapa de construcción
FROM maven:3-eclipse-temurin-17 AS build
WORKDIR /app
COPY projectBookStore /app
WORKDIR /app
ENV LANG C.UTF-8
RUN mvn clean package -DskipTests


# Etapa de ejecución
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY . /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
