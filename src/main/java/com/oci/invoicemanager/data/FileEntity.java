package com.oci.invoicemanager.data;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
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
    @ManyToOne
    @JoinColumn(name = "INVOICEID", nullable = false)
    @JsonManagedReference
    InvoiceEntity invoiceId;
}
