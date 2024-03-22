# FROM openjdk:21
# VOLUME /tmp
# EXPOSE 8080
# ARG DEPENDENCY=target
# ADD ${DEPENDENCY}/*.jar api-gateway.jar
# ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/api-gateway.jar"]
# docker build -t api-gateway .
# docker run -d -p 8080:8080 --name="api-gateway" api-gateway

FROM openjdk:21-jdk
EXPOSE 8090
COPY target/*.jar app.jar
ENTRYPOINT ["java",  "-jar", "/app.jar"]

