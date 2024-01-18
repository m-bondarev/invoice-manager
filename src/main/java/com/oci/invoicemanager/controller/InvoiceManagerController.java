package com.oci.invoicemanager.controller;

import com.oci.invoicemanager.data.InvoiceDescription;
import com.oci.invoicemanager.data.InvoiceDto;
import com.oci.invoicemanager.data.InvoiceEntity;
import com.oci.invoicemanager.service.InvoiceManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/invoices", produces = APPLICATION_JSON_VALUE)
public class InvoiceManagerController {
    private final InvoiceManagerService invoiceManagerService;

    @GetMapping
    public List<InvoiceEntity> getAll() {
        return invoiceManagerService.getAllInvoices();
    }

    @GetMapping(value = "/{invoiceId}")
    public InvoiceDescription getById(@PathVariable("invoiceId") Long invoiceId) {
        return invoiceManagerService.getInvoice(invoiceId);
    }

    @PostMapping(consumes = {
            APPLICATION_JSON_VALUE,
            MULTIPART_FORM_DATA_VALUE
    })
    public InvoiceDescription create(
            @RequestPart(name = "invoice") InvoiceDto invoice,
            @RequestPart(name = "file", required = false) MultipartFile file) {
        return invoiceManagerService.createInvoice(invoice, file);
    }

    //DOTO
    @PutMapping(value = "/{invoiceId}")
    public InvoiceDescription update(
            @PathVariable("invoiceId") Long invoiceId,
            @RequestPart(name = "invoice") InvoiceDto invoice,
            @RequestPart(name = "file", required = false) MultipartFile file) {
        return invoiceManagerService.updateInvoice(invoiceId, invoice, file);
    }

    @DeleteMapping(value = "/{invoiceId}")
    public void delete(@PathVariable("invoiceId") Long invoiceId) {
        invoiceManagerService.delete(invoiceId);
    }
}
