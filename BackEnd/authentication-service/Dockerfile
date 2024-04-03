
FROM maven:latest AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
WORKDIR /usr/src/app
RUN mvn -DskipTests=true clean package

FROM openjdk:21
COPY --from=build /usr/src/app/target/*.jar app.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "/app.jar"]
