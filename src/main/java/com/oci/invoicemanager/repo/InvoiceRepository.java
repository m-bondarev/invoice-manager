package com.oci.invoicemanager.repo;

import com.oci.invoicemanager.data.InvoiceEntity;
import com.oci.invoicemanager.data.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {
    @Modifying
    @Transactional
    @Query("update InvoiceEntity i set i.status = :status where i.id in :idList")
    void updateStatus(@Param(value = "idList") List<Long> idList, @Param(value = "status") InvoiceStatus status);
}
