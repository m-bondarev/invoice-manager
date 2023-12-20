package com.oci.invoicemanager.service;

import com.oci.invoicemanager.data.InvoiceDto;
import com.oci.invoicemanager.data.InvoiceStatus;
import com.oci.invoicemanager.data.PublishMessage;
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
    private final InvoiceDAO invoiceDAO;

    public List<InvoiceDto> getAllInvoices(Optional<String> userId, Optional<InvoiceStatus> invoiceStatus) {
        List<InvoiceDto> invoices = invoiceDAO.getAllInvoices()
                .stream()
                .map(invoiceEntity -> InvoiceDto.builder()
                        .userId(invoiceEntity.userId())
                        .description(invoiceEntity.description())
                        .status(invoiceEntity.status())
                        .build()).toList();
         objectStorageService.listObjects(invoices.invoiceId(), 50);
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
//         objectStorageService.deleteObject(fileId);
    }
}
