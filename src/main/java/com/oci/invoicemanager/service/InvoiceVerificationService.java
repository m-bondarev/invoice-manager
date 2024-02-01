package com.oci.invoicemanager.service;

import com.oci.invoicemanager.data.InvoiceStatus;
import com.oci.invoicemanager.repo.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class InvoiceVerificationService {

    private final QueueService queueService;
    private final InvoiceRepository invoiceRepository;

    @Scheduled(fixedDelay = 4000)
    public void verifyInvoice() {
        log.info("Processing...");
        //TODO some validation....
        invoiceRepository.updateStatus(queueService.getMessages(), InvoiceStatus.VERIFIED);
    }
}
