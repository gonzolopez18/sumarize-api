FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/tenpo-0.0.1.jar .
EXPOSE 8080
CMD ["java", "-jar", "tenpo-0.0.1.jar"]