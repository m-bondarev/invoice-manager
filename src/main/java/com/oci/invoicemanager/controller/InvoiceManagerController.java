package com.oci.invoicemanager.controller;

import com.oci.invoicemanager.data.InvoiceDto;
import com.oci.invoicemanager.data.InvoiceStatus;
import com.oci.invoicemanager.data.PublishMessage;
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
    private final NotificationService notificationService;

    @GetMapping
    public List<String> getAll(
            @RequestParam Optional<String> userId,
            @RequestParam Optional<InvoiceStatus> invoiceStatus) {
        return invoiceManagerService.getAllInvoices(userId, invoiceStatus);
    }

    @GetMapping(value = "/{invoiceId}")
    public String getById(@PathVariable String invoiceId) {
        return invoiceManagerService.getInvoice(invoiceId);
    }

    @PostMapping
    public InvoiceDto create(@RequestBody InvoiceDto invoiceDto, @RequestParam("file") MultipartFile file, ModelMap modelMap) {
        invoiceManagerService.createInvoice(invoiceDto, file);
        return invoiceDto;
    }

    @DeleteMapping(value = "/{invoiceId}")
    public void delete(@PathVariable String invoiceId) {
        invoiceManagerService.delete(invoiceId);
    }

    @PostMapping("/publishMessage")
    public String publishMessage(@RequestBody PublishMessage publishMessage) {
        return notificationService.publishMessage(publishMessage).toString();
    }
}
