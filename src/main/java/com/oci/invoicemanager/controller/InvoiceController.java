package com.oci.invoicemanager.controller;

import com.oci.invoicemanager.invoice.InvoiceDto;
import com.oci.invoicemanager.service.InvoiceManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/invoices", produces = APPLICATION_JSON_VALUE)
public class InvoiceController {

    private final InvoiceManagerService invoiceService;

    @GetMapping
    public String getAll() {
        return null;
    }

    @GetMapping(value = "/{id}")
    public String getById(@PathVariable final long id) {
        return null;
    }

    @PostMapping
    public String publishMessage(@RequestBody InvoiceDto invoiceDto) {
        return null;
    }
}
