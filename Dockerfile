FROM amazoncorretto:21 AS BUILDER

COPY gradle gradle/
COPY gradlew settings.gradle build.gradle ./
COPY . .

CMD gradlew --no-daemon -i clean build

COPY /build/libs/invoice-manager-0.0.1-SNAPSHOT.jar invoice-manager-app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "invoice-manager-app.jar"]
