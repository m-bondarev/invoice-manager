package com.oci.invoicemanager.data;

import lombok.Builder;

@Builder
public record InvoiceDto(
		Long userId,
		String description) {
}
