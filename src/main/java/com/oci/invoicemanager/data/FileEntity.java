package com.oci.invoicemanager.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;

@Entity
@Builder
@Table(name = "FILES")
public record FileEntity(@Id Long id, String url, Long invoiceId) {
}
