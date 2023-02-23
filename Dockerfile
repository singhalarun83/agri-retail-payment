FROM openjdk:17-jdk-slim
MAINTAINER agri-retail
WORKDIR /app
COPY ./target/MAINTAINER baeldung.com /app
EXPOSE 8090
CMD ["java", "-jar", "payment-0.0.1-SNAPSHOT.jar"]