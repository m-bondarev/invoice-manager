package com.oci.invoicemanager.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JoinColumn(name = "USERID")
    UserEntity user;
    String description;
    InvoiceStatus status;
    @OneToMany(mappedBy = "invoiceId",
            cascade = CascadeType.ALL)
    @JsonIgnore
    List<FileEntity> files;
}
