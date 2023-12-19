package com.oci.invoicemanager.data;

import java.util.UUID;

public record UserDto(UUID userId, String name, String surname, String email) {
}
