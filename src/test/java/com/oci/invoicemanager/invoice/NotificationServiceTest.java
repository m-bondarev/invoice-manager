package com.oci.invoicemanager.invoice;

import com.oci.invoicemanager.controller.InvoiceController;
import com.oci.invoicemanager.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NotificationServiceTest {
    @Autowired
    private NotificationService notificationService;

    @Test
    void r() {
        notificationService.createSubscription("1@godeltech.com");
        notificationService.publishMessage(new NotificationService.PublishMessage("Test msg", "test title"));
    }
}