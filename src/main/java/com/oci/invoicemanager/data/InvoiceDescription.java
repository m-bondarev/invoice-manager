package com.oci.invoicemanager.data;

import lombok.Builder;

import java.util.List;

@Builder
public record InvoiceDescription(
		Long id,
		String userName,
		String userSurname,
		String description,
		InvoiceStatus status,
		List<String> additions) {
}
