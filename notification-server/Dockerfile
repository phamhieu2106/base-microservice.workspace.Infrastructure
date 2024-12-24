FROM openjdk:17-jdk-slim

# Copy file JAR từ thư mục com.henry.build/libs/
COPY com.henry.build/libs/notification-server-1.0.0.jar /app/notification-server.jar

# Mở port cho service
EXPOSE 7003

# Lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "/app/notification-server.jar"]
