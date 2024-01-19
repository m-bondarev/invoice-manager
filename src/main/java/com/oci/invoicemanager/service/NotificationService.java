package com.oci.invoicemanager.service;

import com.oci.invoicemanager.data.PublishMessage;
import com.oracle.bmc.ons.NotificationDataPlaneClient;
import com.oracle.bmc.ons.model.CreateSubscriptionDetails;
import com.oracle.bmc.ons.model.MessageDetails;
import com.oracle.bmc.ons.requests.CreateSubscriptionRequest;
import com.oracle.bmc.ons.requests.DeleteSubscriptionRequest;
import com.oracle.bmc.ons.requests.GetSubscriptionRequest;
import com.oracle.bmc.ons.requests.PublishMessageRequest;
import com.oracle.bmc.ons.responses.CreateSubscriptionResponse;
import com.oracle.bmc.ons.responses.DeleteSubscriptionResponse;
import com.oracle.bmc.ons.responses.PublishMessageResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    @Value("${oci.notification.topicId}")
    private String topicId;
    @Value("${oci.notification.compartmentId}")
    private String compartmentId;

    private final NotificationDataPlaneClient client;

    public PublishMessageResponse publishMessage(PublishMessage publishMessage) {

        final var messageDetails = MessageDetails.builder()
                .title(publishMessage.title())
                .body(publishMessage.message())
                .build();

        final var publishMessageRequest = PublishMessageRequest.builder()
                .topicId(topicId)
                .messageDetails(messageDetails)
                .opcRequestId(UUID.randomUUID().toString())
                .messageType(PublishMessageRequest.MessageType.RawText).build();

        return client.publishMessage(publishMessageRequest);
    }

    public CreateSubscriptionResponse createSubscription(String email) {
        final var createSubscriptionDetails = CreateSubscriptionDetails.builder()
                .topicId(topicId)
                .compartmentId(compartmentId)
                .protocol("EMAIL")
                .endpoint(email)
                .build();

        final var createSubscriptionRequest = CreateSubscriptionRequest.builder()
                .createSubscriptionDetails(createSubscriptionDetails)
                .opcRequestId(UUID.randomUUID().toString()).build();

        return client.createSubscription(createSubscriptionRequest);
    }

    public DeleteSubscriptionResponse deleteSubscription(String subscriptionId) {
        final var deleteSubscriptionDetails = DeleteSubscriptionRequest.builder()
                .opcRequestId(UUID.randomUUID().toString())
                .subscriptionId(subscriptionId)
                .build();

        return client.deleteSubscription(deleteSubscriptionDetails);
    }
}
