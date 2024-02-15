# FROM openjdk:21
# VOLUME /tmp
# EXPOSE 8086
# ARG DEPENDENCY=target
# ADD ${DEPENDENCY}/*.jar api-gateway.jar
# ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/api-gateway.jar"]
# docker build -t api-gateway .
# docker run -d -p 8086:8086 --name="appointment" appointment

FROM openjdk:21-jdk
EXPOSE 8086
COPY target/*.jar app.jar

ENTRYPOINT ["java",  "-jar", "/app.jar"]

