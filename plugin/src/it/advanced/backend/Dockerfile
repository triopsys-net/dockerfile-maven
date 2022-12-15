FROM eclipse-temurin:11-jre-alpine

LABEL MAINTAINER="David Flemström <dflemstr@spotify.com>"

COPY target/lib /usr/share/backend/lib
COPY target/backend.jar /usr/share/backend/backend.jar

ENTRYPOINT ["java", "-jar", "/usr/share/backend/backend.jar"]
