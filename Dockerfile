# Используем базовый образ OpenJDK для Java 11
FROM openjdk:11-jre-slim

# Устанавливаем рабочую директорию в /app
WORKDIR /app

# Копируем собранный JAR файл внутрь контейнера
COPY target/hospital-managment-system.jar /app/hospital-managment-system.jar

# Указываем команду для запуска приложения при старте контейнера
CMD ["java", "-jar", "hospital-managment-system.jar"]
