FROM arm64v8/maven:3.9.0-amazoncorretto-19

ENV TZ=America/Sao_Paulo

COPY . .

RUN mvn clean package -DskipTests

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "target/cineapi.jar"]