FROM openjdk:8

COPY target/expensetrackerapi.jar expensetrackerapi.jar

ENTRYPOINT ["java","-jar","/expensetrackerapi.jar"]