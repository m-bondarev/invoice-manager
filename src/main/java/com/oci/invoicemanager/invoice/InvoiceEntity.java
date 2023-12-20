package com.oci.invoicemanager.invoice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvoiceEntity {
    private Long id;
    private Long userId;
    private String description;
    private String status;

}
