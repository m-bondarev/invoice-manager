package com.oci.invoicemanager.service;

import com.oci.invoicemanager.data.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvoiceManagerService {
    private final NotificationService notificationService;
    private final ObjectStorageService objectStorageService;
    private final QueueService queueService;
    private final InvoiceEntityRepository repository;

    public List<InvoiceEntity> getAllInvoices(Optional<Long> userId, Optional<InvoiceStatus> invoiceStatus) {
        return repository.findAllByUserIdAndStatus(userId, invoiceStatus);
    }

    public InvoiceDescription getInvoice(Long invoiceId) {
        InvoiceEntity invoice = repository.findById(invoiceId).orElse(null);
        if (Objects.isNull(invoice)) return null;

        List<String> descriptions = invoice.files().stream()
                .map(file -> objectStorageService.getTextFile("%s/%s".formatted(invoice.id(), file.url())))
                .toList();
        return toDescription(invoice, descriptions);
    }

    public InvoiceDescription createInvoice(InvoiceDto invoice, MultipartFile file) {
        queueService.publish(invoice);

        InvoiceEntity saved = repository.save(InvoiceEntity.builder()
                .user(UserEntity.builder().id(invoice.userId()).build())
                .status(invoice.status())
                .files(List.of())
                .build());
        String filePath = objectStorageService.putTextFile(saved.id(), file);

        notificationService.publishMessage(new PublishMessage("New invoice", invoice.description()));

        return toDescription(saved, List.of());
    }

    public void delete(Long invoiceId) {
        repository.deleteById(invoiceId);
        objectStorageService.deleteObject("%s/".formatted(invoiceId));
    }

    private InvoiceDescription toDescription(InvoiceEntity invoice, List<String> descriptions) {
        return InvoiceDescription.builder()
                .id(invoice.id())
                .userName(invoice.user().getName())
                .userSurname(invoice.user().getSurname())
                .description(invoice.description())
                .status(invoice.status())
                .additions(descriptions)
                .build();
    }
}
