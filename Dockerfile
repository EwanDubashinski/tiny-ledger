# Build stage
FROM eclipse-temurin:21-jdk-jammy AS builder
WORKDIR /app
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
# Download dependencies first (cached layer)
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Run stage
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
# Create a non-root user for security
RUN addgroup --system javauser && adduser --system --ingroup javauser javauser
COPY --from=builder /app/target/*.jar app.jar
# Set ownership to non-root user
RUN chown -R javauser:javauser /app
USER javauser
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]