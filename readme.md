# Automation Company Manager — Microservices Architecture (Educational Project)

A full-stack project focused on exploring and learning **microservices architecture** in the context of a company management system.

The application simulates a simplified automation company environment and was built to experiment with service decomposition, infrastructure components, and communication patterns commonly used in modern backend systems.

> ⚠️ This project is educational in nature — many aspects are simplified to better understand architectural concepts.

---

## 🚀 Highlights

- Microservices architecture with Spring Cloud ecosystem  
- API Gateway + Service Discovery (Eureka)  
- Centralized configuration (Config Server)  
- Secure authentication with Keycloak (OAuth2 + JWT)  
- Hybrid approach: Spring MVC + WebFlux  
- Event-driven communication with Kafka  
- Type-safe frontend integration (Angular + OpenAPI)  

---

## 🧠 Project Focus

- Learning how to structure microservices-based systems  
- Understanding communication between services  
- Exploring Spring Cloud ecosystem components  
- Applying DDD-inspired modular design  
- Building a realistic backend architecture in practice  

---

## 🏗️ System Architecture

The system is divided into three main layers:

### 1. Frontend
Angular application acting as a client for backend services.

### 2. Infrastructure Layer

Handles cross-cutting concerns:

- Config Server — centralized configuration  
- Service Registry — service discovery (Eureka)  
- API Gateway — single entry point for requests  
- Keycloak — authentication and authorization  

### 3. Business Services

Each service is independently deployable and follows a consistent structure:

- Controller  
- Service layer  
- Repository  
- DTOs & Mappers  
- Exception handling  

---

## 🔧 Services Overview

### 👷 Employee Service
- Employee management  
- Standard Spring MVC (blocking)

### 📁 Project Service
- Project and task management  
- Uses WebFlux (reactive approach)

### 📢 Notification Service
- System notifications  
- Prepared for event-driven communication  

### 📦 Common Domain
- Shared models and reusable components  

---

## ⚙️ Technology Stack

### Backend
- Java 17  
- Spring Boot  
- Spring Web (REST) & Spring WebFlux  
- Spring Security (OAuth2 Resource Server + JWT)  
- Keycloak (authentication & authorization)  
- Spring Data JPA (Hibernate)  
- Flyway (database migrations)  
- Spring Cloud (Config Server, Eureka, API Gateway)  
- Apache Kafka  
- MapStruct  
- Lombok  
- SpringDoc OpenAPI  
- Spring Boot Actuator  

---

### Frontend
- Angular 20  
- Tailwind CSS  
- RxJS  
- Chart.js  
- Keycloak Angular  
- OpenAPI Generator  

---

### Infrastructure & DevOps
- Docker & Docker Compose  
- Maven  
- PostgreSQL  
- H2 (development profile)  

---

## 🔐 Security

- Authentication handled via Keycloak  
- OAuth2 Resource Server + JWT  
- Frontend integration via Keycloak Angular 

---

## 🗄️ Configuration Management

- Centralized configuration via Config Server  
- Environment-based configs (`dev` / `prod`)  
- Externalized configuration per service  

---

## 🚀 Getting Started

### Prerequisites

- Java 17+  
- Node.js 18+  
- Docker & Docker Compose  

---