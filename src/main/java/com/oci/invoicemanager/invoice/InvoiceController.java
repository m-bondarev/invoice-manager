package com.oci.invoicemanager.invoice;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/v1/invoices", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InvoiceController {

  private final InvoiceService invoiceService;

  @GetMapping
  public String getAll() {
    return invoiceService.getMessages();
  }

  @GetMapping(value = "/{id}")
  public String getById(
      @PathVariable final long id
  ) {
    return invoiceService.getMessages();
  }

  @PostMapping
  public String publishMessage(@RequestBody InvoiceDto invoiceDto) {
    return invoiceService.publish(invoiceDto).toString();
  }

    @GetMapping("/invoices")
    public List<InvoiceDto> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }
}
