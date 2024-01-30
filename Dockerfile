FROM maven:3-eclipse-temurin-21 as build
COPY src /home/app/src
COPY frontend /home/app/frontend
COPY pom.xml /home/app
COPY .git /home/app/.git
RUN mvn -f /home/app/pom.xml clean package -Pproduction

FROM eclipse-temurin:21
LABEL org.opencontainers.image.source="https://github.com/joel-schaltenbrand/SkiCheck"
COPY --from=build /home/app/target/skicheck.jar /usr/local/lib/skicheck.jar
EXPOSE 80
ENTRYPOINT ["java", "-jar", "/usr/local/lib/skicheck.jar"]
