package com.oci.invoicemanager.repo;

import com.oci.invoicemanager.data.InvoiceEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

public interface InvoiceRepository extends ListCrudRepository<InvoiceEntity, Long> {

    @Modifying
    @Query("update InvoiceEntity i set i.description = :description, i.status = com.oci.invoicemanager.data.InvoiceStatus.UPDATED where i.id = :id")
    void updateDescription(@Param(value = "id") long id, @Param(value = "description") String description);
}
