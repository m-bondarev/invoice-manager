package com.oci.invoicemanager.service;

import com.oci.invoicemanager.data.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final NotificationService notificationService;

    public void createUser(UserDto userDto) {
        // save to db
        notificationService.createSubscription(userDto.email());
    }
}
