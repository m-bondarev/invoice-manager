package com.oci.invoicemanager.data;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Builder;

@Entity
@Builder
@Table(name = "invoices")
public record InvoiceEntity(
    @Id
    Long id,
    @OneToOne(cascade = CascadeType.ALL)
    UserEntity user,
    String description,
    InvoiceStatus status,
    @OneToMany
    List<FileEntity> files
) {

}
