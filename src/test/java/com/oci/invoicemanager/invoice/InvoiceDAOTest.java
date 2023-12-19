package com.oci.invoicemanager.invoice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class InvoiceDAOTest {
    @Autowired
    private InvoiceDAO invoiceDAO;

    @Test
    void getAllInvoices_success() {
        System.out.println(invoiceDAO.getAllInvoices());
    }
}