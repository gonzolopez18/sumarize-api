FROM openjdk:17-jdk-slim
WORKDIR /app
#COPY wait-for-it.sh ./wait-for-it.sh
COPY target/tenpo-0.0.1.jar .
EXPOSE 8080
CMD ["java", "-jar", "tenpo-0.0.1.jar"]