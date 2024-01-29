package com.oci.invoicemanager.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "AUDIT_APP")
public class AuditRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    String eventId;
    @Column(name = "RESTART_DATE")
    Timestamp restartDate;
}
