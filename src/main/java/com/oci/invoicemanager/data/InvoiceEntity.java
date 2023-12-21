package com.oci.invoicemanager.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "INVOICES")
public class InvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="USERID", nullable=false)
    UserEntity user;
    String description;
    InvoiceStatus status;
    @OneToMany(mappedBy="invoiceId")
    List<FileEntity> files;
}
