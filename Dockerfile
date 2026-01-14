FROM openjdk:26-ea-slim

# Set working directory (docker creates a folder /app)
WORKDIR /app

# Copy the JAR file to created folder
COPY target/load-balancer-ms-1.0.0.jar .

ENTRYPOINT ["java", "-jar", "/app/load-balancer-ms-1.0.0.jar"]

# docker build --tag maratmingazovr/load-balancer-ms:1.0 .
# docker run \
#  -p 8080:8080 \
#  maratmingazovr/load-balancer-ms:1.0 \

# -p <host_port>:<container_port>