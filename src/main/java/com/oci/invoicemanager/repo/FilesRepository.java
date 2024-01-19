package com.oci.invoicemanager.repo;

import com.oci.invoicemanager.data.FileEntity;
import org.springframework.data.repository.CrudRepository;

public interface FilesRepository extends CrudRepository<FileEntity, Long> {
    boolean existsByInvoiceId(Long invoiceId);
}
