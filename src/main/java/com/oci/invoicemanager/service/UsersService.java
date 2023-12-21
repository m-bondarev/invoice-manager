package com.oci.invoicemanager.service;

import com.oci.invoicemanager.data.UserEntity;
import com.oracle.bmc.ons.responses.CreateSubscriptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final NotificationService notificationService;
    private final UserRepository repository;

    public UserEntity createUser(UserEntity userEntity) {
        UserEntity save = repository.save(userEntity);
        CreateSubscriptionResponse subscription = notificationService.createSubscription(userEntity.getEmail());
        return save;
    }

    public void deleteUser(Long userId) {
        repository.deleteById(userId);
        // delete subscription
    }

    public List<UserEntity> getAll(){
        return (List<UserEntity>) repository.findAll();
    }
}
