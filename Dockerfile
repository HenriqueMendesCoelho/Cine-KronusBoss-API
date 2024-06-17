FROM maven:3.9.7-amazoncorretto-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM amazoncorretto:21-alpine3.19
ENV TZ=America/Sao_Paulo
WORKDIR /app
COPY --from=build /app/target/cineapi.jar /app/cineapi.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/cineapi.jar"]
