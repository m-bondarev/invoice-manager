FROM arm64v8/amazoncorretto:21 AS BUILDER

COPY gradle gradle/
COPY gradlew settings.gradle build.gradle ./
COPY . .

CMD gradlew --no-daemon -i clean build

COPY /build/libs/invoice-manager-0.0.1-SNAPSHOT.jar invoice-manager-app.jar

EXPOSE 8080

RUN --mount=type=secret,id=OCI_CLI_KEY_CONTENT \
    --mount=type=secret,id=OCI_CLI_REGION \
    --mount=type=secret,id=OCI_CLI_USER \
    --mount=type=secret,id=OCI_CLI_TENANCY \
    --mount=type=secret,id=OCI_CLI_FINGERPRINT

ENTRYPOINT ["java", "-jar", "invoice-manager-app.jar"]
