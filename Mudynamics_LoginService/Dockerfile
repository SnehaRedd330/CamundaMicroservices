FROM adoptopenjdk/openjdk8:latest
ADD target/Mudynamics-Login-service-1.1.0.jar Mudynamics-Login-service-1.1.0.jar
RUN sh -c 'touch /Mudynamics-Login-service-1.1.0.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","Mudynamics-Login-service-1.1.0.jar"]
