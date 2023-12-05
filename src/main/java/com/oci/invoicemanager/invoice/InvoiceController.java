package com.oci.invoicemanager.invoice;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/invoices", produces = APPLICATION_JSON_VALUE)
public class InvoiceController {

  @GetMapping
  public String getAll() {
    return "{}";
  }

  @GetMapping(value = "/{id}")
  public String getById(
      @PathVariable final long id
  ) {
    return """
        {
          "id": "%s"
        }
        """.formatted(id);
  }

  @PostMapping
  public InvoiceDto create(
      @RequestBody final InvoiceDto invoiceDto
  ) {
    return invoiceDto;
  }
}
