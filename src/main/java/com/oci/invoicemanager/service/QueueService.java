package com.oci.invoicemanager.service;

import com.oci.invoicemanager.data.InvoiceDto;
import com.oracle.bmc.queue.QueueClient;
import com.oracle.bmc.queue.model.PutMessagesDetails;
import com.oracle.bmc.queue.model.PutMessagesDetailsEntry;
import com.oracle.bmc.queue.requests.GetMessagesRequest;
import com.oracle.bmc.queue.requests.PutMessagesRequest;
import com.oracle.bmc.queue.responses.PutMessagesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class QueueService {
    private final QueueClient queueClient;

    @Value("${oci.queue.ocid}")
    private String queueId;

    public PutMessagesResponse publish(InvoiceDto invoiceDto) {
        log.info("Publishing message {}", invoiceDto.userId());

        final var putMessagesRequest = PutMessagesRequest.builder()
                .queueId(queueId)
                .body$(PutMessagesDetails.builder().messages(List.of(PutMessagesDetailsEntry.builder()
                                .content(invoiceDto.toString())
                                .build()))
                        .build())
                .build();

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
