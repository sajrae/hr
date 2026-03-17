FROM eclipse-temurin:21-jdk-alpine
LABEL authors="Admin"
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-Dvaadin.productionMode=true", "-jar", "app.jar"]