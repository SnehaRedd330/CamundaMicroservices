FROM adoptopenjdk/openjdk8:latest
ADD target/metadataservice-1.1.0.jar metadataservice-1.1.0.jar
RUN sh -c 'touch /metadataservice-1.1.0.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","metadataservice-1.1.0.jar"]
