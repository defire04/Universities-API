#
# Build stage
#
FROM maven:3.8.5 AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests

#
# Package stage
#
FROM openjdk:17-jdk-alpine
COPY --from=build /target/Universities-API-0.0.1-SNAPSHOT.jar Universities-API-0.0.1-SNAPSHOT.jar
# ENV PORT=8080
EXPOSE 8080

CMD ["java","-jar","Universities-API-0.0.1-SNAPSHOT.jar"]