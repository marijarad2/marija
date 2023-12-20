#stage 1: build app
FROM maven:3.8.6-openjdk-18 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install

#stage 2: Run app
FROM openjdk:18
WORKDIR /app
COPY --from=build /app/target/SpringBootChatAppSoSpri-0.0.1-SNAPSHOT.jar ./aws.jar
EXPOSE 8080
CMD ["java","-jar","aws.jar"]