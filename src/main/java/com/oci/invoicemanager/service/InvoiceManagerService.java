package com.oci.invoicemanager.service;

import com.oci.invoicemanager.data.InvoiceDto;
import com.oci.invoicemanager.data.InvoiceStatus;
import com.oci.invoicemanager.data.PublishMessage;
import com.oci.invoicemanager.data.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public List<String> getAllInvoices(Optional<String> userId, Optional<InvoiceStatus> invoiceStatus) {
//        InvoiceEntity invoice  = new InvoiceEntity(); - get from db
//         objectStorageService.listObjects(invoice.invoiceId(), 50);
        // create full descriptions representation
        return List.of();
    }

    public String getInvoice(String invoiceId) {
//        InvoiceEntity invoice = new InvoiceEntity(); // get from db
//        List<String> descriptions = invoice.filePaths().stream().map(objectStorageService::getTestFile).toList();
        // create full descriptions representation
        return "";
    }

    public void createInvoice(InvoiceDto invoice, MultipartFile file) {
        queueService.publish(invoice);

        UUID fileId = UUID.randomUUID();
        String fileName = "%s/%s".formatted(invoice.invoiceId(), fileId);
        objectStorageService.putTextFile(fileName, file);

        // save to db

        notificationService.publishMessage(new PublishMessage("New invoice", invoice.description()));
    }

    public void delete(String invoiceId) {
        // delete from db
        // objectStorageService.deleteObject(invoiceId);
    }
}
