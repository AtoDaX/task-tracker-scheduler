FROM openjdk:17-oracle as builder
LABEL authors="atopiraka"
RUN mkdir -p /app/source
COPY . /app/source
WORKDIR /app/source
RUN ./mvnw clean package

FROM openjdk:17-oracle as runtime
LABEL authors="atopiraka"
COPY --from=builder /app/source/target/*.jar /app/app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/app/app.jar"]