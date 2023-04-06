FROM openjdk:15-jdk-alpine
COPY . /app
WORKDIR /app
ENV MY_PROP=my_value
CMD ["java", "-jar", "your-application.jar"]