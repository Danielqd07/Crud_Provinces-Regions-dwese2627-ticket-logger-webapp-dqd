# ==================
# STAGE 1: build
# ==================

FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /app

# Copiamos primero pom.xml para aprovechar la caché de Docker
COPY pom.xml .

# Descargamos dependencias
RUN mvn -q -e -B dependency:go-offline

# Copiamos el código fuente
COPY src ./src

# Compilamos el proyecto
RUN mvn -q -e -B clean package -DskipTests


# ==================
# STAGE 2: run
# ==================

FROM eclipse-temurin:21-jre-jammy

# Creamos un usuario que no sea root
RUN useradd -ms /bin/bash spring

WORKDIR /app

# Copiamos solamente el jar generado en la etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Creamos la carpeta donde se guardarán las imágenes
RUN mkdir -p /app/uploads && chown -R spring:spring /app

# Ejecutaremos la aplicación como usuario spring
USER spring

# Puerto utilizado por Spring Boot
EXPOSE 8080

# Opciones de memoria de Java
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Ejecutar Spring Boot
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]