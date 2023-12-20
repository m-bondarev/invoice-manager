package com.oci.invoicemanager.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;

import java.util.List;

@Entity
@Builder
@Table(name = "invoices")
public record InvoiceEntity(
        @Id Long id,
        UserEntity user,
        String description,
        InvoiceStatus status,
        List<FileEntity> files) {
}
