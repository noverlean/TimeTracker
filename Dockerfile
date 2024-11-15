# Используем официальное Java 17 JDK изображение
FROM amazoncorretto:22

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем JAR-файл приложения в контейнер
COPY target/TimeTracker-0.0.1-SNAPSHOT.jar app.jar

# Открываем порт, на котором работает Spring Boot
EXPOSE 8080

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]