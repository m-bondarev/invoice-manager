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
    private final AuthenticationDetailsProvider provider;
    @Value("${oci.ostorage.nameSpace}")
    private String namespace;
    @Value("${oci.ostorage.bucketName}")
    private String bucketName;

    public List<String> listObjects(String prefix, Integer limit) {

        try (ObjectStorageClient client = ObjectStorageClient.builder()
                .build(provider)) {

            ListObjectsRequest listObjectsRequest = ListObjectsRequest.builder()
                    .namespaceName(namespace)
                    .bucketName(bucketName)
                    .prefix(prefix)
                    .limit(limit)
                    .build();

            ListObjectsResponse response = client.listObjects(listObjectsRequest);

            if (HttpStatusCode.valueOf(response.get__httpStatusCode__()).isError()) {
                throw new IllegalStateException(
                        "List Objects err with status code %s".formatted(response.get__httpStatusCode__()));
            }

            return response.getListObjects().getObjects()
                    .stream().map(ObjectSummary::getName).toList();
        }
    }

    public String getTextFile(String objName) {
        try (ObjectStorageClient client = ObjectStorageClient.builder()
                .build(provider)) {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .namespaceName(namespace)
                    .bucketName(bucketName)
                    .objectName(objName)
                    .build();

            GetObjectResponse response = client.getObject(getObjectRequest);

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
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .namespaceName(namespace)
                    .bucketName(bucketName)
                    .objectName("%s/%s".formatted(invoiceId, file.getName()))
                    .contentType(TEXT_CONTENT)
                    .body$(new ByteArrayInputStream(file.getBytes()))
                    .build();
            PutObjectResponse response = client.putObject(putObjectRequest);
            if (HttpStatusCode.valueOf(response.get__httpStatusCode__()).isError()) {
                throw new IllegalStateException("Put Object err with status code %s".formatted(response.get__httpStatusCode__()));
            }
            return file.getName();
        } catch (IOException e) {
            throw new IllegalStateException("OOps", e);
        }
    }

    public void deleteObject(String prefix) {
        try (ObjectStorageClient client = ObjectStorageClient.builder()
                .build(provider)) {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .namespaceName(namespace)
                    .bucketName(bucketName)
                    .objectName(prefix)
                    .build();

            DeleteObjectResponse response = client.deleteObject(deleteObjectRequest);
            if (HttpStatusCode.valueOf(response.get__httpStatusCode__()).isError()) {
                throw new IllegalStateException(
                        "Delete Objects err with status code %s".formatted(response.get__httpStatusCode__()));
            }
        }
    }
}
