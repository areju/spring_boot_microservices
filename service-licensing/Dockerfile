#stage 1
#Start with a base image containing Java runtime
FROM openjdk:11-slim as build

# Add Maintainer Info
LABEL maintainer="Mallikarjuna kuruva <arjunflex@gmail.com>"

WORKDIR application

# The application's jar file
ARG JAR_FILE=target/*.jar

# Add the application's jar to the container
COPY ${JAR_FILE} application.jar

#unpackage jar file
RUN java -Djarmode=layertools -jar application.jar extract

#stage 2
#Same Java runtime
FROM openjdk:11-slim

WORKDIR application

#Copy unpackaged application to new container
ARG DEPENDENCY=/target/dependency
COPY --from=build application/dependencies/ ./ 
COPY --from=build application/spring-boot-loader/ ./
COPY --from=build application/snapshot-dependencies/ ./
COPY --from=build application/application/ ./


#execute the application
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
