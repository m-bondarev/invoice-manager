package com.oci.invoicemanager.objstorage;

import com.oci.invoicemanager.service.ObjectStorageService;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class ObjectStorageServiceTest {
    private static final String OBJ_NAME = "a1/test.txt";
    private static final String OBJ_CONTEXT = "Hello OCI";
    @Autowired
    private AuthenticationDetailsProvider provider;
    @Autowired
    private ObjectStorageService storageManager;

    @Test
    @Order(1)
    void listObjects_success() {
        List<String> objectSummaries = storageManager.listObjects("a1/", 10);
        assertEquals(List.of("a1/firstwallpaperbetter.jpg", "a1/test.jpg"), objectSummaries);
    }

    @Test
    @SneakyThrows
    @Order(3)
    void getObject_success() {
        byte[] object = storageManager.getObject(OBJ_NAME);
        assertEquals(OBJ_CONTEXT, new String(object, StandardCharsets.UTF_8));
    }

    @Test
    @Order(2)
    void putObject_success() {
        assertDoesNotThrow(() -> storageManager.putObject(
                OBJ_NAME,
                "text/plain",
                new ByteArrayInputStream(OBJ_CONTEXT.getBytes())));
    }

    @Test
    @Order(4)
    void deleteObject_success() {
        assertDoesNotThrow(() -> storageManager.deleteObject(OBJ_NAME));
    }
}
