package com.oci.invoicemanager.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "FileEntity")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "FILES")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    String url;
    @Column(name = "INVOICE_ID")
    Long invoiceId;
}
