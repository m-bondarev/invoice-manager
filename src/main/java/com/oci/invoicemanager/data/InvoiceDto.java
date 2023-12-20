package com.oci.invoicemanager.data;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record InvoiceDto(
        UUID invoiceId,
        Long userId,
        String description,
        LocalDate createdDate,
        LocalDate updatedDate,
        LocalDate endDate,
        InvoiceStatus status) {
}
