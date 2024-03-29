package com.oci.invoicemanager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oci.invoicemanager.data.*;
import com.oci.invoicemanager.repo.FilesRepository;
import com.oci.invoicemanager.repo.InvoiceRepository;
import com.oci.invoicemanager.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class InvoiceManagerService {

    private final NotificationService notificationService;
    private final ObjectStorageService objectStorageService;
    private final QueueService queueService;
    private final FilesRepository filesRepository;
    private final InvoiceRepository invoiceRepository;
    private final UserRepository userRepository;

    public List<InvoiceEntity> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public InvoiceDescription getInvoice(Long invoiceId) {
        final var invoice = invoiceRepository.findById(invoiceId).orElse(null);

        if (Objects.isNull(invoice)) {
            return null;
        }

        final var descriptions = invoice.getFiles().stream()
                .map(file -> objectStorageService.getTextFile("%s/%s".formatted(invoice.getId(), file.getUrl())))
                .toList();

        return toDescription(invoice, descriptions);
    }

    @Transactional
    public InvoiceDescription createInvoice(InvoiceDto invoice, MultipartFile file) throws JsonProcessingException {
        final var saved = invoiceRepository.save(InvoiceEntity.builder()
                .user(userRepository.findById(invoice.userId()).orElseThrow())
                .description(invoice.description())
                .status(InvoiceStatus.NEW)
                .build());
        storeFile(file, saved.getId());
        queueService.publish(saved);
        notificationService.publishMessage(new PublishMessage("New invoice", invoice.description()));

        return toDescription(saved, List.of(getFileContent(file)));
    }

    @Transactional
    public Long updateInvoice(Long invoiceId,
                              InvoiceDto invoice,
                              MultipartFile file) {
        InvoiceEntity entity = invoiceRepository.findById(invoiceId).orElseThrow(() -> new IllegalArgumentException("Invoice do not exist"));
        if (invoice.userId() != null) entity.setUser(userRepository.findById(invoice.userId()).orElseThrow());
        if (invoice.description() != null) entity.setDescription(invoice.description());
        invoiceRepository.save(entity);

        storeFile(file, invoiceId);
        return invoiceId;
    }

    @Transactional
    public void delete(Long invoiceId) {
        if (filesRepository.existsByInvoiceId(invoiceId)) {
            objectStorageService.deleteObject(invoiceId.toString());
        }

        invoiceRepository.deleteById(invoiceId);
    }

    @SneakyThrows
    private String getFileContent(MultipartFile file) {
        return new String(file.getBytes(), StandardCharsets.UTF_8);
    }

    private void storeFile(MultipartFile file, Long invoiceId) {
        if (Objects.nonNull(file)) {
            String filePath = objectStorageService.putTextFile(invoiceId, file);
            filesRepository.save(FileEntity.builder().invoiceId(invoiceId).url(filePath).build());
        }
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
