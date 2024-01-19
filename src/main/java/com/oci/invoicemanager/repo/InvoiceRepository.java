package com.oci.invoicemanager.repo;

import com.oci.invoicemanager.data.InvoiceEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface InvoiceRepository extends ListCrudRepository<InvoiceEntity, Long> {
}
