package com.oci.invoicemanager.data;

import lombok.Builder;

import java.util.UUID;

@Builder
public record InvoiceDto(
        Long userId,
        String description,
        InvoiceStatus status) {
}
