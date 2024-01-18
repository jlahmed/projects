FROM ubuntu:latest

# Install OpenJDK and X11 components
RUN apt-get update \
    && apt-get install -y openjdk-21-jdk-headless xorg \
    && rm -rf /var/lib/apt/lists/*

ENV DISPLAY=:0

# Copy the JAR file into the container 
COPY SecretWord.jar .

# Define the command to run the application when the container starts
CMD ["java", "-jar", "SecretWord.jar"]
