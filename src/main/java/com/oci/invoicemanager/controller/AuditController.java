package com.oci.invoicemanager.controller;

import com.oci.invoicemanager.data.AuditRecord;
import com.oci.invoicemanager.repo.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/audit", produces = APPLICATION_JSON_VALUE)
public class AuditController {
    private final AuditService auditService;

    @GetMapping
    public List<AuditRecord> getAudit(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return auditService.findAllByRestartDateBetween(from, to);
    }

    @Transactional
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public AuditRecord create(@RequestBody AuditRecord auditRecord) {
        log.info("Audit record ==========" + auditRecord);
        return auditService.save(auditRecord);
    }

    @DeleteMapping
    @Transactional
    public void delete(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        auditService.deleteAllByRestartDateBefore(date);
    }
}
