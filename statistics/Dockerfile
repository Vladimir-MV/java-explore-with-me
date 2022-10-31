FROM amazoncorretto:11-alpine-jdk
COPY target/*.jar appStatistics.jar
ENTRYPOINT ["java","-jar","/appStatistics.jar"]