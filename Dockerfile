FROM amazoncorretto:17
COPY /target/*.jar /build/spring.jar

ENTRYPOINT ["java",  "-jar","/build/spring.jar"]