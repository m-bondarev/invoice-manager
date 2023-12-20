package com.oci.invoicemanager.data;

import lombok.Builder;

@Builder
public record InvoiceEntity(
        Long id,
        Long userId,
        String description,
        InvoiceStatus status) {
}
