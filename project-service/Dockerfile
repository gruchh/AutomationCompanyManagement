#STAGE 1:
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw package -DskipTests

# STAGE 2:
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
RUN addgroup --system spring && adduser --system --ingroup spring spring
USER spring:spring
COPY --from=builder /app/target/*.jar app.jar
ENV SPRING_PROFILES_ACTIVE=prod
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "app.jar"]