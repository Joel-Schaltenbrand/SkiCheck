FROM maven:3-eclipse-temurin-22 AS build
COPY src /home/app/src
COPY frontend /home/app/frontend
COPY pom.xml /home/app
COPY .git /home/app/.git
RUN mvn -f /home/app/pom.xml clean package -P prd

FROM eclipse-temurin:21
LABEL org.opencontainers.image.source="https://github.com/joel-schaltenbrand/SkiCheck"
LABEL org.opencontainers.image.description="This is a simple application to check the status of the employees of a ski resort. This project is for learning purposes only. It belongs to the module M306 of the ICT Berufsbildung Schweiz."
COPY --from=build /home/app/target/skicheck.jar /usr/local/lib/skicheck.jar
EXPOSE 80
ENTRYPOINT ["java", "-jar", "/usr/local/lib/skicheck.jar"]
