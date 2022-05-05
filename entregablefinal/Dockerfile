#
FROM maven:3.6.3-jdk-8 as BUILD
COPY target/app-0.0.1-SNAPSHOT.jar app.jar
COPY entregablefinal/pom.xml pom.xml
RUN mvn -f /entregablefinal/pom.xml clean package

EXPOSE 8080

