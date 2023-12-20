package com.oci.invoicemanager.service;

import com.oci.invoicemanager.data.InvoiceEntity;
import com.oci.invoicemanager.data.InvoiceStatus;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface InvoiceEntityRepository extends ListCrudRepository<InvoiceEntity, Long> {
    List<InvoiceEntity> findAllByUserIdAndStatus(Optional<Long> userId, Optional<InvoiceStatus> status);
}
