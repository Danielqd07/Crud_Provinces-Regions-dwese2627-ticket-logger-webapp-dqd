# Ticket Logger WebApp

Proyecto realizado en 2º DAW para el módulo de Desarrollo Web en Entorno Servidor.

## Tecnologías utilizadas

- Java 21
- Spring Boot
- Thymeleaf
- JDBC Template
- MariaDB
- Maven
- Docker
- Bootstrap 5

## Funcionalidades

- CRUD de Comunidades Autónomas
- CRUD de Provincias
- Relación 1:N entre regiones y provincias
- Validaciones
- Internacionalización en español e inglés
- Uso de Thymeleaf para las vistas
- Uso de MariaDB como base de datos

## Base de datos

La aplicación utiliza MariaDB mediante Docker.

Para iniciar la base de datos:

```bash
docker compose up -d
