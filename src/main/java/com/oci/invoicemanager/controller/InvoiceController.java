package com.oci.invoicemanager.controller;

import com.oci.invoicemanager.data.InvoiceDto;
import com.oci.invoicemanager.data.PublishMessage;
import com.oci.invoicemanager.service.InvoiceManagerService;
import com.oci.invoicemanager.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/invoices", produces = APPLICATION_JSON_VALUE)
public class InvoiceController {
    private final InvoiceManagerService invoiceManagerService;
    private final NotificationService notificationService;

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

    @PostMapping("/publishMessage")
    public String publishMessage(@RequestBody PublishMessage publishMessage) {
        return notificationService.publishMessage(publishMessage).toString();
    }
}
