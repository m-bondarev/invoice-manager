package com.oci.invoicemanager.service;

import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.model.ObjectSummary;
import com.oracle.bmc.objectstorage.requests.DeleteObjectRequest;
import com.oracle.bmc.objectstorage.requests.GetObjectRequest;
import com.oracle.bmc.objectstorage.requests.ListObjectsRequest;
import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
import com.oracle.bmc.objectstorage.responses.DeleteObjectResponse;
import com.oracle.bmc.objectstorage.responses.GetObjectResponse;
import com.oracle.bmc.objectstorage.responses.ListObjectsResponse;
import com.oracle.bmc.objectstorage.responses.PutObjectResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ObjectStorageService {

    public static final String TEXT_CONTENT = "text/plain";

    @Value("${oci.ostorage.nameSpace}")
    private String namespace;
    @Value("${oci.ostorage.bucketName}")
    private String bucketName;

    private final AuthenticationDetailsProvider provider;

    public List<String> listObjects(String prefix, Integer limit) {

        try (ObjectStorageClient client = ObjectStorageClient.builder()
                .build(provider)) {

            final var listObjectsRequest = ListObjectsRequest.builder()
                    .namespaceName(namespace)
                    .bucketName(bucketName)
                    .prefix(prefix)
                    .limit(limit)
                    .build();

            final var response = client.listObjects(listObjectsRequest);

            if (HttpStatusCode.valueOf(response.get__httpStatusCode__()).isError()) {
                throw new IllegalStateException(
                        "List Objects err with status code %s".formatted(response.get__httpStatusCode__()));
            }

            return response.getListObjects().getObjects()
                    .stream()
                    .map(ObjectSummary::getName)
                    .toList();
        }
    }

    public String getTextFile(String objName) {
        try (ObjectStorageClient client = ObjectStorageClient.builder()
                .build(provider)) {
            final var getObjectRequest = GetObjectRequest.builder()
                    .namespaceName(namespace)
                    .bucketName(bucketName)
                    .objectName(objName)
                    .build();

            final var response = client.getObject(getObjectRequest);

            if (!HttpStatusCode.valueOf(response.get__httpStatusCode__()).isError()) {
                return new String(response.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            }

            throw new IllegalStateException("Get Object err with status code %s".formatted(response.get__httpStatusCode__()));
        } catch (IOException e) {
            throw new IllegalStateException("OOps", e);
        }
    }

    public String putTextFile(Long invoiceId, MultipartFile file) {
        try (ObjectStorageClient client = ObjectStorageClient.builder()
                .build(provider)) {
            final var putObjectRequest = PutObjectRequest.builder()
                    .namespaceName(namespace)
                    .bucketName(bucketName)
                    .objectName("%s/%s".formatted(invoiceId, file.getOriginalFilename()))
                    .contentType(TEXT_CONTENT)
                    .body$(new ByteArrayInputStream(file.getBytes()))
                    .build();

            final var response = client.putObject(putObjectRequest);

            if (HttpStatusCode.valueOf(response.get__httpStatusCode__()).isError()) {
                throw new IllegalStateException("Put Object err with status code %s".formatted(response.get__httpStatusCode__()));
            }

            return file.getOriginalFilename();
        } catch (IOException e) {
            throw new IllegalStateException("OOps", e);
        }
    }

    @Transactional
    public void deleteObject(String prefix) {
        try (ObjectStorageClient client = ObjectStorageClient.builder()
                .build(provider)) {
            listObjects(prefix, null)
                    .forEach(object -> {
                        final var response = client.deleteObject(generateDeleteRequest(object));

                        if (HttpStatusCode.valueOf(response.get__httpStatusCode__()).isError()) {
                            throw new IllegalStateException(
                                    "Delete Objects err with status code %s".formatted(response.get__httpStatusCode__()));
                        }
                    });
        }
    }

    private DeleteObjectRequest generateDeleteRequest(String prefix) {
        return DeleteObjectRequest.builder()
                .namespaceName(namespace)
                .bucketName(bucketName)
                .objectName(prefix)
                .build();
    }
}
