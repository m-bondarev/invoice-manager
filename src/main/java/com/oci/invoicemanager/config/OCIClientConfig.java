package com.oci.invoicemanager.config;

import com.oracle.bmc.Region;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.SimpleAuthenticationDetailsProvider;
import com.oracle.bmc.auth.StringPrivateKeySupplier;
import com.oracle.bmc.ons.NotificationDataPlaneClient;
import com.oracle.bmc.queue.QueueClient;
import java.io.InputStream;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class OCIClientConfig {

  @ConfigurationProperties(prefix = "oci.client")
  public record OCIClientProps(String tenancyId, String userId, String fingerprint,
                               String privateKey, Region region) {

  }

  @Bean
  public AuthenticationDetailsProvider authenticationDetailsProvider(OCIClientProps clientProps) {

    log.info(clientProps.toString());

    return SimpleAuthenticationDetailsProvider.builder()
        .tenantId(clientProps.tenancyId())
        .userId(clientProps.userId())
        .fingerprint(clientProps.fingerprint())
        .privateKeySupplier(new StringPrivateKeySupplier(clientProps.privateKey()))
        .region(clientProps.region)
        .build();
  }

  @Bean
  public NotificationDataPlaneClient notificationDataPlaneClient(
      AuthenticationDetailsProvider provider) {
    return NotificationDataPlaneClient.builder().build(provider);
  }

  @Bean
  public QueueClient queueClient(
      AuthenticationDetailsProvider authenticationDetailsProvider
  ) {
    return QueueClient.builder()
        .endpoint("https://cell-1.queue.messaging.eu-frankfurt-1.oci.oraclecloud.com")
        .build(authenticationDetailsProvider);
  }


  private Supplier<InputStream> getKey(String key) {
    key = key.replaceAll(" ", "\n");
    key = key.replace("-----BEGIN\nPRIVATE\nKEY-----", "-----BEGIN PRIVATE KEY-----");
    key = key.replace("-----END\nPRIVATE\nKEY-----", "-----END PRIVATE KEY-----");

    return new StringPrivateKeySupplier(key);
  }
}
