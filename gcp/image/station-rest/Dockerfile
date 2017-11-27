# Set the base image to open jdk 8
FROM openjdk:8

# Run mkdir command to create directory /myapp in this image
RUN mkdir /station-rest

# Copy host build directory to /myapp directory in the image
COPY . /station-rest

# Set /myapp as the working directory 
WORKDIR /station-rest

# Docker container listens to 8080
EXPOSE 8080

# Configure container as an executable
ENTRYPOINT java -jar station-rest-0.0.1-SNAPSHOT-exec.jar
