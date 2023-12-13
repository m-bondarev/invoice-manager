package com.oci.invoicemanager.config;

import com.oracle.bmc.Region;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.SimpleAuthenticationDetailsProvider;
import com.oracle.bmc.auth.StringPrivateKeySupplier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.util.function.Supplier;

@Configuration
public class ObjectStorageConfigs {
    @Bean
    public AuthenticationDetailsProvider authenticationDetailsProvider(ObjectStorageProps storageProps) {
        return SimpleAuthenticationDetailsProvider.builder()
                .tenantId(storageProps.tenancyId())
                .userId(storageProps.userId())
                .fingerprint(storageProps.fingerprint())
                .privateKeySupplier(getKey(storageProps.privateKey()))
                .region(storageProps.region())
                .build();
    }

    @ConfigurationProperties(prefix = "ostorage")
    public record ObjectStorageProps(String tenancyId,
                                     String userId,
                                     String fingerprint,
                                     String privateKey,
                                     Region region,
                                     String namespace,
                                     String bucketName) {
    }

    private Supplier<InputStream> getKey(String key) {
//        StringBuilder builder = new StringBuilder("-----BEGIN PRIVATE KEY-----\n")
//                .append(key)
//                .append("\n-----END PRIVATE KEY-----");

        key = key.replaceAll(" ", "\n");
        key = key.replace("-----BEGIN\nPRIVATE\nKEY-----", "-----BEGIN PRIVATE KEY-----");
        key = key.replace("-----END\nPRIVATE\nKEY-----", "-----END PRIVATE KEY-----");

        return new StringPrivateKeySupplier(key);
    }
}
