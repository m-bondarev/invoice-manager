FROM amazoncorretto:21 AS BUILDER

COPY gradle gradle/
COPY gradlew settings.gradle build.gradle ./
COPY . .

CMD gradlew --no-daemon -i clean build

COPY invoice-manager-0.0.1-SNAPSHOT.jar /

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]