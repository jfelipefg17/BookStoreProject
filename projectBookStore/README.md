# BookStoreProject

Este es un proyecto desarrollado en Java 17 utilizando Spring Boot 3.4.2 y Maven. Es una aplicación web para una librería en la que se utilizan Thymeleaf para las vistas, Spring Security para la autenticación y JPA para la gestión de datos.

# Tecnologías utilizadas

Java 17

Spring Boot 3.4.2

Spring Security

Spring Data JPA

Thymeleaf

Maven


# Requisitos previos

Antes de ejecutar el proyecto, asegúrese de tener instalado en su sistema:

Java 17

Maven

MySQL u otra base de datos compatible

# Configuración del proyecto

Clonar el repositorio

git clone <URL_DEL_REPOSITORIO>
cd projectBookStore

# Configurar la base de datos

Crear una base de datos en MySQL:

CREATE DATABASE book_store;

# Configurar el archivo application.properties o application.yml con las credenciales de acceso a la base de datos:

spring.datasource.url=jdbc:mysql://localhost:3306/book_store
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Compilar y ejecutar el proyecto

Para compilar el proyecto:

mvn clean install

Para ejecutar la aplicación:

mvn spring-boot:run

Acceder a la aplicación

Una vez iniciada, la aplicación estará disponible en:

http://localhost:8080

# Pruebas

Para ejecutar las pruebas, use el siguiente comando:

mvn test

Despliegue

# Para desplegar la aplicación en un entorno de producción, puede generar un archivo JAR ejecutable:

mvn package
java -jar target/projectBookStore-0.0.1-SNAPSHOT.jar

# Contribución

Si deseas contribuir a este proyecto, sigue los siguientes pasos:

Haz un fork del repositorio.

Crea una nueva rama con tu función o corrección: git checkout -b mi-nueva-funcionalidad

Realiza los cambios y haz commit: git commit -m "Agregada nueva funcionalidad"

Sube los cambios: git push origin mi-nueva-funcionalidad

Crea un Pull Request.

