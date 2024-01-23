package com.oci.invoicemanager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oci.invoicemanager.data.InvoiceEntity;
import com.oracle.bmc.queue.QueueClient;
import com.oracle.bmc.queue.model.GetMessage;
import com.oracle.bmc.queue.model.PutMessagesDetails;
import com.oracle.bmc.queue.model.PutMessagesDetailsEntry;
import com.oracle.bmc.queue.requests.GetMessagesRequest;
import com.oracle.bmc.queue.requests.PutMessagesRequest;
import com.oracle.bmc.queue.responses.PutMessagesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class QueueService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final QueueClient queueClient;

    @Value("${oci.queue.ocid}")
    private String queueId;

    public PutMessagesResponse publish(final InvoiceEntity invoice) throws JsonProcessingException {
        log.info("Publishing message {}", invoice.getId());

        final var putMessagesRequest = PutMessagesRequest.builder()
                .queueId(queueId)
                .body$(PutMessagesDetails.builder().messages(List.of(PutMessagesDetailsEntry.builder()
                                .content(OBJECT_MAPPER.writeValueAsString(invoice))
                                .build()))
                        .build())
                .build();

        return queueClient.putMessages(putMessagesRequest);
    }

    public List<Long> getMessages() {
        log.info("Retrieving messages");

        final var messagesResponse = queueClient.getMessages(GetMessagesRequest.builder()
                .queueId(queueId)
                .limit(10)
                .build());
        if (HttpStatusCode.valueOf(messagesResponse.get__httpStatusCode__()).isError()) {
            throw new IllegalStateException(
                    "Get Messages err with status code %s".formatted(messagesResponse.get__httpStatusCode__()));
        }

        return messagesResponse.getGetMessages().getMessages().stream()
                .map(GetMessage::getContent)
                .map(QueueService::toEntity)
                .filter(Objects::nonNull)
                .map(InvoiceEntity::getId)
                .toList();
    }

    private static InvoiceEntity toEntity(String s) {
        try {
            return OBJECT_MAPPER.readValue(s, InvoiceEntity.class);
        } catch (JsonProcessingException e) {
            log.warn("Error during processing message: %s".formatted(e.getMessage()));
            return null;
        }
    }
}
