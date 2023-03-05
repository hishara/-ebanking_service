FROM amazoncorretto:11
MAINTAINER hishararc@gmail.com
COPY target/ebanking_service-1.0.0.jar ebanking_service_1.0.0.jar
ENTRYPOINT ["java","-jar","/ebanking_service_1.0.0.jar"]