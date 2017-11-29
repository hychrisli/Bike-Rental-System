# Set the base image to open jdk 8
FROM openjdk:8

# Run mkdir command to create directory /station-pubsub in this image
RUN mkdir /station-pubsub

# Copy host build directory to /station-pubsub directory in the image
COPY . /station-pubsub/

# Set /station-pubsub as the working directory 
WORKDIR /station-pubsub

# Configure container as an executable
ENTRYPOINT java -jar station-pubsub-0.0.1-SNAPSHOT-exec.jar
