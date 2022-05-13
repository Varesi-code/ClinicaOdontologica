FROM maven:3.6.3-jdk-8 as BUILD
COPY /entregablefinal/src /home/app/src
COPY /entregablefinal/pom.xml /home/app/pom.xml
RUN mvn -f /home/app/pom.xml clean package
