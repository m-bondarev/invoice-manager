package com.oci.invoicemanager.service;

import com.oci.invoicemanager.data.InvoiceDto;
import com.oci.invoicemanager.data.PublishMessage;
import com.oci.invoicemanager.data.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvoiceManagerService {
    private final NotificationService notificationService;
    private final ObjectStorageService objectStorageService;
    private final QueueService queueService;

    public void createUser(UserDto userDto) {
        // save to db
        notificationService.createSubscription(userDto.email());
    }

    public void createInvoice(InvoiceDto invoice) {
        queueService.publish(invoice);
        objectStorageService.putTextFile(invoice);
        // save to db
        notificationService.publishMessage(new PublishMessage("New invoice", invoice.description()));
    }

    public void getInvoice() {
        
    }
}
