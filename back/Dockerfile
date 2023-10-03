FROM maven:3.9.4-amazoncorretto-17 AS build
COPY src /home/hotel-service/src
COPY pom.xml /home/hotel-service
RUN mvn -f /home/hotel-service/pom.xml clean package -Dmaven.test.skip=true


FROM openjdk:19
COPY --from=build /home/hotel-service/target/back-2.0.0.jar /usr/local/lib/hotel.jar
LABEL authors="smlunev"
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/hotel.jar"]