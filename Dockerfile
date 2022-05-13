FROM maven:3-amazoncorretto-17 as BUILD
COPY /entregablefinal/src /home/app/src
COPY /entregablefinal/pom.xml /home/app/pom.xml
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:17
WORKDIR /usr/src
COPY --from=BUILD /home/app/target/app-0.0.1-SNAPSHOT.jar /usr/src/app.jar
EXPOSE 8080
ENTRYPOINT [ java  -jar /usr/src/app.jar"]



