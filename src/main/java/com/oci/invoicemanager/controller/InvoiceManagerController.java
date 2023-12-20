package com.oci.invoicemanager.controller;

import com.oci.invoicemanager.data.*;
import com.oci.invoicemanager.service.InvoiceManagerService;
import com.oci.invoicemanager.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/invoices", produces = APPLICATION_JSON_VALUE)
public class InvoiceManagerController {
    private final InvoiceManagerService invoiceManagerService;

    @GetMapping
    public List<InvoiceEntity> getAll(
            @RequestParam Optional<Long> userId,
            @RequestParam Optional<InvoiceStatus> invoiceStatus) {
        return invoiceManagerService.getAllInvoices(userId, invoiceStatus);
    }

    @GetMapping(value = "/{invoiceId}")
    public InvoiceDescription getById(@PathVariable Long invoiceId) {
        return invoiceManagerService.getInvoice(invoiceId);
    }

    @PostMapping
    public InvoiceDescription create(@RequestBody InvoiceDto invoiceDto, @RequestParam("file") MultipartFile file, ModelMap modelMap) {
        return invoiceManagerService.createInvoice(invoiceDto, file);
    }

    @DeleteMapping(value = "/{invoiceId}")
    public void delete(@PathVariable Long invoiceId) {
        invoiceManagerService.delete(invoiceId);
    }
}
