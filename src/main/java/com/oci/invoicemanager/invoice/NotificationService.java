package com.oci.invoicemanager.invoice;

import com.oci.invoicemanager.invoice.InvoiceController.PublishMessage;
import com.oracle.bmc.ons.NotificationDataPlaneClient;
import com.oracle.bmc.ons.model.MessageDetails;
import com.oracle.bmc.ons.requests.PublishMessageRequest;
import com.oracle.bmc.ons.responses.PublishMessageResponse;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

  @Value("${oci.notification.topicId}")
  private String topicId;
  @Autowired
  private NotificationDataPlaneClient client;

  public PublishMessageResponse publishMessage(PublishMessage publishMessage) {

    MessageDetails messageDetails = MessageDetails.builder()
        .title(publishMessage.title())
        .body(publishMessage.message())
        .build();

    PublishMessageRequest publishMessageRequest = PublishMessageRequest.builder()
        .topicId(topicId)
        .messageDetails(messageDetails)
        .opcRequestId(UUID.randomUUID().toString())
        .messageType(PublishMessageRequest.MessageType.RawText).build();

    return client.publishMessage(publishMessageRequest);
  }
}
