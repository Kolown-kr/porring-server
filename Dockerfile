FROM azul/zulu-openjdk:17.0.9 AS build
WORKDIR /app
VOLUME /root/.gradle
COPY . .
RUN chmod +x ./gradlew
