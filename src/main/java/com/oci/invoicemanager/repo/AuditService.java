package com.oci.invoicemanager.repo;


import com.oci.invoicemanager.data.AuditRecord;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public interface AuditService extends CrudRepository<AuditRecord, Long> {
    List<AuditRecord> findAllByRestartDateBetween(LocalDateTime from, LocalDateTime to);

    void deleteAllByRestartDateBefore(LocalDateTime restartDate);
}
