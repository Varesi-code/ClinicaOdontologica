#
FROM maven:3.6.3-jdk-8 as BUILD
COPY src /home/app/src
COPY pom.xml /home/app/pom.xml
RUN mvn -f /home/app/pom.xml clean package

EXPOSE 8080

