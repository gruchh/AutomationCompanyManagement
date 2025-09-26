# Automation Company Management System

This project is a cloud-native management system for a small automation company, built using a **microservices architecture** with Java and the Spring Boot framework. The system is designed to be scalable, resilient, and secure, leveraging modern technologies for messaging, monitoring, and identity management.

## Architecture

The application is decomposed into smaller, independent services communicating asynchronously via Apache Kafka and registered dynamically in a service registry (Eureka).

### Components
* **External Identity Provider (Keycloak)**: Handles authentication & authorization.
* **API Gateway (Spring Cloud Gateway)**: Single entry point, routes requests, validates JWT tokens.
* **Service Registry (Eureka)**: Enables service discovery.
* **Config Server**: Centralized configuration for all services.
* **Project Service**: Manages projects and tasks, publishes events to Kafka.
* **Employee Service**: Manages employee data.
* **Notification Service**: Subscribes to Kafka events, delivers real-time notifications via WebSockets.
* **Monitoring & Observability**: Spring Boot Actuator, Micrometer with Prometheus + Grafana.
* **API Documentation**: Swagger UI powered by SpringDoc OpenAPI.
* **Apache Kafka (KRaft mode)**: Central event bus.

### Database Strategy
Polyglot persistence approach:
* **PostgreSQL**: Structured relational data (projects, employees).
* **MongoDB**: Flexible storage for notifications.

## Technology Stack
* Java 17+
* Spring Boot & Spring Cloud (Gateway, Config, Eureka)
* Spring Security (OAuth2 Resource Server)
* Apache Kafka
* Spring Data JPA + PostgreSQL
* Spring Data MongoDB
* Spring WebSocket
* Keycloak
* Spring Boot Actuator + Micrometer + Prometheus
* SpringDoc OpenAPI (Swagger UI)
* Maven
* Docker & Docker Compose

## Getting Started

### Prerequisites
* JDK 17+
* Apache Maven
* Docker + Docker Compose

### Running the Application
Run the full stack with Docker Compose (`docker-compose.yml`):
1. PostgreSQL
2. MongoDB
3. Apache Kafka (KRaft mode)
4. Keycloak
5. Config Server
6. Eureka Service Registry
7. API Gateway
8. Business microservices (`project-service`, `employee-service`, `notification-service`)
9. Frontend application (see below)

## Frontend

The frontend (e.g., React, Angular, or Vue) is developed as a **separate module** under the root project:

