FROM openjdk:17-jdk-slim
MAINTAINER agri-retail
WORKDIR /app
COPY ./target/payment-0.0.1-SNAPSHOT.jar /app
EXPOSE 8090
CMD ["java", "-jar", "payment-0.0.1-SNAPSHOT.jar"]