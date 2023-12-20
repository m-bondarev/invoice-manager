package com.oci.invoicemanager.service;

import com.oci.invoicemanager.data.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final NotificationService notificationService;
    private final UserRepository repository;

    public UserEntity createUser(UserEntity userEntity) {
        UserEntity save = repository.save(userEntity);
        notificationService.createSubscription(userEntity.getEmail());
        return save;
    }

    public void deleteUser(Long userId) {
        repository.deleteById(userId);
        // delete subscription
    }
}
