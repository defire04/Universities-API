#FROM maven:3.8.5-openjdk-17 AS build
#COPY . .
#RUN mvn clean package -DskipTests
#
##
## Package stage
##
#FROM openjdk:17-jdk
#COPY --from=build /target/Universities-API-0.0.1-SNAPSHOT.jar Universities-API-0.0.1-SNAPSHOT.jar
## ENV PORT=8080
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","Universities-API-0.0.1-SNAPSHOT.jar"]
#CMD ["java -version"]
#FROM eclipse-temurin:17-jdk-alpine
#COPY target/Universities-API-0.0.1-SNAPSHOT.jar app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]
#EXPOSE 8080