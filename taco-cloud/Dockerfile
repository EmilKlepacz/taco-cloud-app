####  STAGE 1: Install dependencies and go offline
FROM eclipse-temurin:17-jdk-jammy AS deps

WORKDIR /build

# Copy Maven wrapper and configuration
COPY --chmod=0755 mvnw mvnw
COPY .mvn/ .mvn/
COPY pom.xml pom.xml

# Copy the manually built DTO JAR and its POM
COPY taco-cloud-dto-1.0-SNAPSHOT.jar /tmp/
COPY taco-cloud-dto-pom.xml /tmp/pom.xml

# Install DTO jar into Maven local repo and resolve all dependencies offline
RUN ./mvnw install:install-file \
      -Dfile=/tmp/taco-cloud-dto-1.0-SNAPSHOT.jar \
      -DpomFile=/tmp/pom.xml && \
    ./mvnw dependency:go-offline -DskipTests

### STAGE 2: Build the application
FROM deps AS package

WORKDIR /build

COPY ./src src/
COPY pom.xml pom.xml

# Build the application and copy it to app.jar
RUN ./mvnw package -DskipTests && \
    cp target/*.jar target/app.jar

### STAGE 3: Minimal runtime container
FROM eclipse-temurin:17-jre-jammy AS final

# Create a non-root app user
ARG UID=10001
RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "/nonexistent" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid "${UID}" \
    appuser

USER appuser

COPY --from=package /build/target/app.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]