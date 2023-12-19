package com.oci.invoicemanager.invoice;

import com.oracle.bmc.queue.QueueClient;
import com.oracle.bmc.queue.model.PutMessagesDetails;
import com.oracle.bmc.queue.model.PutMessagesDetailsEntry;
import com.oracle.bmc.queue.requests.GetMessagesRequest;
import com.oracle.bmc.queue.requests.PutMessagesRequest;
import com.oracle.bmc.queue.responses.PutMessagesResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InvoiceService {

  private final QueueClient queueClient;
  private final NotificationService notificationService;

  @Value("${oci.queue.ocid}")
  private String queueId;

  public PutMessagesResponse publish(
      final InvoiceDto invoiceDto
  ) {
    log.info("Publishing message {}", invoiceDto.getUserId());

    final var putMessagesRequest = PutMessagesRequest.builder()
        .queueId(queueId)
        .body$(PutMessagesDetails.builder().messages(List.of(PutMessagesDetailsEntry.builder()
                .content(invoiceDto.toString())
                .build()))
            .build())
        .build();

//    save to storage

//    save to db

//    notificationService.publishMessage(putMessagesRequest);

    return queueClient.putMessages(putMessagesRequest);
  }

  public String getMessages() {
    log.info("Retrieving messages");

    final var messages = queueClient.getMessages(GetMessagesRequest.builder()
        .queueId(queueId)
        .build());

    return messages.getGetMessages().toString();
  }
}
