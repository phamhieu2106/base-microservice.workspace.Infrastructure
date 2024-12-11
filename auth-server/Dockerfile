FROM openjdk:17-jdk-slim

# Copy file JAR từ thư mục build/libs/
COPY build/libs/auth-server-1.0.0.jar /app/auth-server.jar

# Mở port cho service
EXPOSE 7001

# Lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "/app/auth-server.jar"]
