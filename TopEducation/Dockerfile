FROM openjdk:17
ARG JAR_FILE=target/TopEducation-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} TopEducation.jar
EXPOSE 6080
ENTRYPOINT ["java","-jar","TopEducation.jar"]