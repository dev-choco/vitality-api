# Stage 1: Build
FROM gradle:9.4-jdk25 AS build
WORKDIR /app
COPY . .
RUN gradle clean bootJar -x test

# Stage 2: Run
FROM amazoncorretto:25-alpine

LABEL maintainer="Vitality"

ENV TZ=America/Bogota
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime \
  && echo $TZ > /etc/timezone

WORKDIR /opt/spring-boot

COPY --from=build /app/build/libs/*.jar vitality-backend.jar

EXPOSE 9000

ENTRYPOINT ["sh", "-c", "java -jar -Dfile.encoding=UTF-8 -Dspring.profiles.active=docker /opt/spring-boot/vitality-backend.jar"]