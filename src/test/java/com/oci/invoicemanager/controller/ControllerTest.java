package com.oci.invoicemanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oci.invoicemanager.InvoiceManagerApplication;
import com.oci.invoicemanager.data.InvoiceDto;
import com.oci.invoicemanager.data.InvoiceStatus;
import com.oci.invoicemanager.data.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = InvoiceManagerApplication.class)
@AutoConfigureMockMvc
class ControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    ObjectMapper mapper;

    @Test
    void getAllUsers() throws Exception {
        mvc.perform(get("/v1/users")
                        .contentType("application/json"))
                .andDo(res -> System.out.println("-------" + res.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void createUser() throws Exception {
        UserEntity user = UserEntity.builder()
                .name("Billy")
                .surename("Bob")
                .email("1@gmail.com")
                .build();
        mvc.perform(post("/v1/users")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteUser() throws Exception {
        mvc.perform(delete("/v1/users/{userId}", 452L))
                .andExpect(status().isOk());
    }

    @Test
    void getAllInvoices() throws Exception {
        mvc.perform(get("/v1/invoices")
                        .contentType("application/json"))
                .andDo(res -> System.out.println("-------" + res.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void createInvoice() throws Exception {
        InvoiceDto invoice = InvoiceDto.builder()
                .userId(425L)
                .description("This is first invoice")
                .status(InvoiceStatus.NEW)
                .build();
        mvc.perform(multipart("/v1/invoices")
                        .file("descr1.txt", "Hello OCI".getBytes(StandardCharsets.UTF_8))
                        .param("invoiceDto", mapper.writeValueAsString(invoice)))
                .andExpect(status().isOk());
    }
}