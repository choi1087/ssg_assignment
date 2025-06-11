FROM openjdk:17-jdk-slim
ARG PROFILE=dev
ENV SPRING_PROFILES_ACTIVE=$PROFILE
WORKDIR /app
COPY build/libs/*.jar app.jar
CMD ["sh", "-c", "java -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE -jar app.jar"]