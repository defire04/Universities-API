FROM maven:3.8.5-openjdk-15 AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests

FROM openjdk15:alpine-jre
COPY --from=build /target/demo-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]