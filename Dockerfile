FROM openjdk:8
EXPOSE 8080
ADD target/spring-boot-app-0.0.1-SNAPSHOT.jar spring-boot-app-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","spring-boot-app-0.0.1-SNAPSHOT.jar"]