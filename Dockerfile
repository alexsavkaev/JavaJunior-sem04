FROM openjdk:21

COPY out/artifacts/sem04_jar2/sem04.jar /tmp/sem04.jar
WORKDIR /tmp

CMD ["java", "-jar", "/tmp/sem04.jar"]