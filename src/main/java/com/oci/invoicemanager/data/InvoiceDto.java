package com.oci.invoicemanager.data;

import java.time.LocalDate;
import java.util.UUID;

public record InvoiceDto(
        UUID userId,
        String description,
        LocalDate createdDate,
        LocalDate updatedDate,
        LocalDate endDate,
        InvoiceStatus status) {
}
