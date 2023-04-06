FROM openjdk:15-jdk-alpine
ARG JAR_FIlE=target/*.jar
COPY ${JAR_FIlE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]