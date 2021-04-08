FROM adoptopenjdk/openjdk8:latest
ADD target/Mudynamics_Workflowservice-1.0.0.jar Mudynamics_Workflowservice-1.0.0.jar
RUN sh -c 'touch /Mudynamics_Workflowservice-1.0.0.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","Mudynamics_Workflowservice-1.0.0.jar"]
