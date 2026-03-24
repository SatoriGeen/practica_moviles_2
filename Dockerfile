# Usar la imagen oficial de Java 25 (Amazon Corretto)
FROM amazoncorretto:25

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo .jar que acabamos de crear
COPY target/backend-api-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto donde corre Spring Boot
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]