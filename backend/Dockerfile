# I use 25 version but in deplyment time neet to change

# this docker file proper work in render

FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app
#COPY . .
#RUN mvn clean package -DskipTests
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY --from=build /app/target/MediCore-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

# How to run with docker-compose.yml file
# docker-compose up --build
# and use other common commands
