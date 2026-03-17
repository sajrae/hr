FROM maven:3.9.11-eclipse-temurin-21

WORKDIR /app

COPY . .

RUN mvn clean package -Dvaadin.productionMode=true -DskipTests

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "target/hr-0.0.1-SNAPSHOT.jar"]