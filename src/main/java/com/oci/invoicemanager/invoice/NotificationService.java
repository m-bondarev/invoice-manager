package com.oci.invoicemanager.invoice;

import com.oracle.bmc.ons.NotificationDataPlaneClient;
import com.oracle.bmc.ons.model.MessageDetails;
import com.oracle.bmc.ons.requests.PublishMessageRequest;
import com.oracle.bmc.ons.responses.PublishMessageResponse;
import com.oracle.bmc.queue.requests.PutMessagesRequest;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

  @Value("${oci.notification.topicId}")
  private String topicId;

  @Autowired
  private NotificationDataPlaneClient client;

  public PublishMessageResponse publishMessage(PutMessagesRequest putMessagesRequest) {

    MessageDetails messageDetails = MessageDetails.builder()
        .title(putMessagesRequest.getPutMessagesDetails().toString())
        .body(putMessagesRequest.getBody$().toString())
        .build();

    PublishMessageRequest publishMessageRequest = PublishMessageRequest.builder()
        .topicId(topicId)
        .messageDetails(messageDetails)
        .opcRequestId(UUID.randomUUID().toString())
        .messageType(PublishMessageRequest.MessageType.RawText).build();

    return client.publishMessage(publishMessageRequest);
  }
}
