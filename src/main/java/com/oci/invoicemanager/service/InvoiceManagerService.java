package com.oci.invoicemanager.service;

import com.oci.invoicemanager.data.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class InvoiceManagerService {
    private final NotificationService notificationService;
    private final ObjectStorageService objectStorageService;
    private final QueueService queueService;
    private final InvoiceEntityRepository invoiceRepository;
    private final UserRepository userRepository;

    public List<InvoiceEntity> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public InvoiceDescription getInvoice(Long invoiceId) {
        InvoiceEntity invoice = invoiceRepository.findById(invoiceId).orElse(null);
        if (Objects.isNull(invoice)) return null;

        List<String> descriptions = invoice.getFiles().stream()
                .map(file -> objectStorageService.getTextFile("%s/%s".formatted(invoice.getId(), file.getUrl())))
                .toList();
        return toDescription(invoice, descriptions);
    }

    public InvoiceDescription createInvoice(InvoiceDto invoice, MultipartFile file) {
        queueService.publish(invoice);

        InvoiceEntity saved = invoiceRepository.save(InvoiceEntity.builder()
                .user(userRepository.findById(invoice.userId()).orElseThrow())
                .status(InvoiceStatus.NEW)
                .files(List.of())
                .build());

        if (Objects.nonNull(file) && !file.isEmpty()) {
            String filePath = objectStorageService.putTextFile(saved.getId(), file);
            saved.setFiles(List.of(FileEntity.builder().url(filePath).build()));
            invoiceRepository.save(saved);
        }

        notificationService.publishMessage(new PublishMessage("New invoice", invoice.description()));

        return toDescription(saved, List.of());
    }

    public void delete(Long invoiceId) {
        invoiceRepository.deleteById(invoiceId);
        objectStorageService.deleteObject("%s/".formatted(invoiceId));
    }

    private InvoiceDescription toDescription(InvoiceEntity invoice, List<String> descriptions) {
        return InvoiceDescription.builder()
                .id(invoice.getId())
                .userName(invoice.getUser().getName())
                .userSurname(invoice.getUser().getSurename())
                .description(invoice.getDescription())
                .status(invoice.getStatus())
                .additions(descriptions)
                .build();
    }
}
