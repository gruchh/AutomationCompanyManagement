# STAGE 1: BUILDER
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app
COPY .mvn .mvn/
COPY mvnw pom.xml ./
COPY services/ services/
COPY infrastructure/ infrastructure/
RUN ./mvnw install -DskipTests -pl infrastructure/config-server -am

# STAGE 2: RUNTIME
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
RUN addgroup --system spring && adduser --system --ingroup spring spring
USER spring:spring
COPY --from=builder /app/infrastructure/config-server/target/*.jar app.jar
ENV SPRING_PROFILES_ACTIVE=prod
ENTRYPOINT ["java", "-jar", "app.jar"]