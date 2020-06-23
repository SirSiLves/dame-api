#dame-api
This is a FFHS school project from the spring 2020. 

The backend is based on a spring boot framework.

Developers are: Roman Bruehlmann and Yves Ruosch

The project is stored here: https://git.ffhs.ch/yves.ruosch/dame-api.git
#



####Pre requirements
- JDK 14
```shell script
run: java --version
-> openjdk 14.0.1 2020-04-14
-> OpenJDK Runtime Environment (build 14.0.1+7)
-> OpenJDK 64-Bit Server VM (build 14.0.1+7, mixed mode, sharing)
```


#
### How to start the local dame-api
```shell script
run: mvn clean install -f pom.xml
run: mvn spring-boot:run
```

###### [ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.8.1:compile (default-compile) on project dame-api: Compilation failure

Means a wrong JDK was tried to use



#
### Swagger-UI
- http://localhost:8080/swagger-ui.html


#
### Prepare docker image
```shell script
run: docker build -t dame-api-docker .
run: docker save dame-api-docker:latest -o "local-export-path" dame-api-docker
```
Import the docker-image to your docker-environment.


