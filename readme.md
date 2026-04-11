# CMMS Lite — Lightweight Maintenance Management System

A full-stack web application for managing maintenance operations in industrial facilities.
Designed to streamline breakdown reporting, spare parts inventory, workforce management,
and shift scheduling — all in one place.

## Architecture

The application follows a **feature-slice / domain-driven** structure on the backend,
with each domain module owning its full vertical slice — controller, service, repository,
mapper, DTO, and exception handling. The Angular frontend is generated from the OpenAPI
spec for a fully type-safe API contract.

### Backend Modules

* **Security**: JWT-based authentication, role-based access control (`ADMIN`, `TECHNICIAN`, `SUBCONTRACTOR`)
* **Breakdown**: Full breakdown lifecycle — anonymous field report → technician assignment → resolution with cost tracking
* **Breakdown Type**: Fault categories (`MECHANICAL`, `AUTOMATIC`, `PARAMETRIC`)
* **Machine**: Equipment CRUD and assignment to breakdowns
* **Spare Part**: Searchable inventory catalogue with automatic usage logging and cost calculation
* **Employee**: Employee profiles, brigade assignment, contract and salary data
* **Shift Schedule**: Multi-brigade (A/B/C/D) rotation generation with DAY / NIGHT / OFF cycles
* **Dashboard**: KPI aggregation — OEE, MTBF, MTTR, weekly efficiency trends
* **Config**: Application configuration and dev data seeding via JavaFaker

### Frontend Modules

* **Core**: Guards, interceptors, type-safe API client (auto-generated from OpenAPI spec)
* **Features**: Business modules — dashboard, home, breakdowns, employees, spare parts, schedules
* **Layout**: Navigation, responsive collapsible sidebar
* **Shared**: Reusable UI components — modals, tables, forms

### Database Strategy

* **PostgreSQL**: Production relational data (breakdowns, employees, machines, spare parts, schedules)
* **H2**: In-memory database for the `dev` profile with automatic sample data seeding

## Technology Stack

**Backend**
* Java 17
* Spring Boot 3.5.4
* Spring Security + JWT
* Spring Data JPA + Hibernate + PostgreSQL
* Flyway — versioned database migrations
* MapStruct + Lombok
* SpringDoc OpenAPI (Swagger UI)
* JavaFaker — dev data seeding

**Frontend**
* Angular ~20
* Tailwind CSS ~4
* FullCalendar ~6 — shift schedule visualisation
* OpenAPI Generator CLI — type-safe HTTP client generation
* ngx-toastr, ng-icons

**Infrastructure**
* Docker & Docker Compose
* Maven

## Screenshots

*Coming soon.*

## Getting Started

### Prerequisites

* JDK 17+
* Node.js 18+
* Apache Maven
* Docker + Docker Compose

### Running the Application

Run the full stack with Docker Compose:

```bash
git clone https://github.com/gruchh/MaintenanceCMMSLite.git
cd MaintenanceCMMSLite
docker-compose up --build
```

Services started:

1. PostgreSQL
2. Backend API (`http://localhost:8080`)
3. Angular Frontend (`http://localhost:4200`)
4. API Documentation — Swagger UI (`http://localhost:8080/swagger-ui-custom.html`)

### Running Locally (without Docker)

**Backend** (H2 in-memory database, auto-seeded with sample data):
```bash
cd backend
./mvnw spring-boot:run -Dspring.profiles.active=dev
```

**Frontend:**
```bash
cd frontend
npm install
npm start
```

### Regenerating the API Client (after backend changes)

```bash
# From local backend
npm run api:generate:dev

# From Docker
npm run api:generate:docker
```

## License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.