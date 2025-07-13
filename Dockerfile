FROM openjdk:17-jdk-alpine

# Expose le port sur lequel l'app écoute
EXPOSE 8089

# Copier le jar dans l'image (ADD peut être remplacé par COPY, plus simple et clair)
COPY target/tp-foyer-5.0.0.jar /app.jar

# Commande pour lancer l'application
ENTRYPOINT ["java", "-jar", "/app.jar"]
