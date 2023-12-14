FROM arm64v8/amazoncorretto:21 AS BUILDER

COPY gradle gradle/
COPY gradlew settings.gradle build.gradle ./
COPY . .

CMD gradlew --no-daemon -i clean build

COPY /build/libs/invoice-manager-0.0.1-SNAPSHOT.jar invoice-manager-app.jar

EXPOSE 8080

ARG OCI_CLI_KEY_CONTENT
ARG OCI_CLI_REGION
ARG OCI_CLI_USER
ARG OCI_CLI_TENANCY
ARG OCI_CLI_FINGERPRINT

ENV OCI_CLI_KEY_CONTENT=${OCI_CLI_KEY_CONTENT}
ENV OCI_CLI_REGION=${OCI_CLI_REGION}
ENV OCI_CLI_USER=${OCI_CLI_USER}
ENV OCI_CLI_TENANCY=${OCI_CLI_TENANCY}
ENV OCI_CLI_FINGERPRINT=${OCI_CLI_FINGERPRINT}

ENTRYPOINT ["java", "-jar", "invoice-manager-app.jar"]
