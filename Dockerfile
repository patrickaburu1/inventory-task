FROM openjdk:11
EXPOSE 8080
ARG JAR_FILE=target/inventory-management-task-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app-invetory.jar
ENV TZ="Africa/Nairobi"
RUN date
ENTRYPOINT ["java","-jar","/app-invetory.jar"]

