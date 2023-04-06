# Используем официальный образ Java 15
FROM openjdk:15-jdk-slim

# Устанавливаем необходимые пакеты
RUN apt-get update && \
    apt-get install -y maven

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем pom.xml и файлы исходного кода в контейнер
COPY pom.xml .
COPY src/ ./src/

# Собираем проект с помощью Maven

RUN mvn package -Djar.finalName=my-app/

# Выставляем порт приложения
EXPOSE 8080

# Запускаем приложение при помощи команды java
CMD ["java", "-jar", "./target/my-app.jar"]