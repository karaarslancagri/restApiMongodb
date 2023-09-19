FROM openjdk:20
ADD target/rest.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]