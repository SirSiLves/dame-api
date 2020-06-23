

FROM openjdk:14-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

# PRODUCTION DEPLOYMENT
# maven -> clean -> install
# docker build -t dame-api-docker .
# docker save dame-api-docker:latest -o "V:\RSLS\Docker\export\dame-api-docker.tar" dame-api-docker
