package com.oci.invoicemanager.invoice;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class InvoiceDto {

  Long userId;
  String description;
  LocalDate createdDate;
  LocalDate updatedDate;
  LocalDate endDate;
  String status;
}
