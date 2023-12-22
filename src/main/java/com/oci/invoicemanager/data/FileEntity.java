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
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "INVOICES",
            joinColumns = @JoinColumn(name = "INVOICEID",
                    referencedColumnName = "ID"))
    @JsonManagedReference
    InvoiceEntity invoiceId;
}
