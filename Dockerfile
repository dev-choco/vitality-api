FROM amazoncorretto:25-alpine

LABEL maintainer="Vitality"

ENV TZ=America/Bogota
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime \
  && echo $TZ > /etc/timezone

WORKDIR /opt/spring-boot

### Copy only the fat jar
COPY build/libs/vitality-backend-1.0.0.jar \
  /opt/spring-boot/vitality-backend-1.0.0.jar

EXPOSE 9000

ENTRYPOINT ["sh", "-c", "java -jar -Dfile.encoding=UTF-8 -D-Dspring.profiles.active=docker /opt/spring-boot/vitality-backend-1.0.0.jar"]