package com.oci.invoicemanager.service;

import com.oci.invoicemanager.InvoiceManagerApplication;
import com.oci.invoicemanager.data.InvoiceDto;
import com.oci.invoicemanager.data.InvoiceStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = InvoiceManagerApplication.class)
class InvoiceManagerServiceTest {
    @Autowired
    private InvoiceManagerService service;

    @Test
    void createInvoice_success(){
        InvoiceDto invoice = InvoiceDto.builder()
                .userId(452L)
                .status(InvoiceStatus.NEW)
                .description("Test test")
                .build();
        MultipartFile file = new MockMultipartFile("descr1.txt", "Hello OCI".getBytes(StandardCharsets.UTF_8));

        service.createInvoice(invoice, file);
    }
}
