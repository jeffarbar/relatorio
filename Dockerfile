FROM openjdk:8-jdk-alpine
RUN mkdir /app
COPY target/*.jar /app/relatorio.jar
WORKDIR /app
EXPOSE 7797
ENTRYPOINT ["java", "-jar", "relatorio.jar"]