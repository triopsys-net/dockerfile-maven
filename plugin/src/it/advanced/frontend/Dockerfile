FROM eclipse-temurin:11-jre-alpine

LABEL MAINTAINER="David Flemström <dflemstr@spotify.com>"

COPY target/lib /usr/share/frontend/lib
COPY target/frontend.jar /usr/share/frontend/frontend.jar

ENTRYPOINT ["java", "-jar", "/usr/share/frontend/frontend.jar"]
