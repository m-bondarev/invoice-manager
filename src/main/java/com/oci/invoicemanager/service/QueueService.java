package com.oci.invoicemanager.service;

import com.oci.invoicemanager.invoice.InvoiceDto;
import org.jvnet.hk2.annotations.Service;

@Service
public class QueueService {
    public PutMessagesResponse publish(final InvoiceDto invoiceDto) {
        return null;
    }
}
