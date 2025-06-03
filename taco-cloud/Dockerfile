### 1. Build dto
FROM eclipse-temurin:17-jdk-jammy AS dto-builder

COPY ./taco-cloud-dto/ /build/

WORKDIR /build

RUN ./mvnw clean package -DskipTests

#### 2. Install dependencies and go offline
FROM eclipse-temurin:17-jdk-jammy AS deps

WORKDIR /build

# Copy Maven wrapper and configuration
COPY --chmod=0755 ./taco-cloud/mvnw mvnw
COPY ./taco-cloud/.mvn/ .mvn/
COPY ./taco-cloud/pom.xml pom.xml

# Install DTO jar into Maven local repo and resolve all dependencies offline
COPY --from=dto-builder /build/target/taco-cloud-dto-1.0-SNAPSHOT.jar /tmp/
COPY --from=dto-builder /build/pom.xml /tmp/

RUN ./mvnw install:install-file -Dfile=/tmp/taco-cloud-dto-1.0-SNAPSHOT.jar -DpomFile=/tmp/pom.xml && \
    ./mvnw dependency:go-offline -DskipTests

### 3. Build the application
FROM deps AS package

WORKDIR /build

COPY ./taco-cloud/src src/
COPY ./taco-cloud/pom.xml pom.xml

# Build the application and copy it to app.jar
RUN ./mvnw package -DskipTests && \
    cp target/*.jar target/app.jar

### 4. Final minimal runtime container
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