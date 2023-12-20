package com.oci.invoicemanager.service;

import com.oci.invoicemanager.data.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
}
