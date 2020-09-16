FROM openjdk:8-jdk-alpine
EXPOSE 8004
ADD target/algogenerator-0.0.1-SNAPSHOT algogenerator.jar
# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/algogenerator.jar"]
