package com.oci.invoicemanager.service;

import com.oci.invoicemanager.data.UserEntity;
import com.oci.invoicemanager.repo.UserRepository;
import com.oracle.bmc.ons.responses.CreateSubscriptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final NotificationService notificationService;
    private final UserRepository repository;

    public List<UserEntity> getAll() {
        return (List<UserEntity>) repository.findAll();
    }

    @Transactional
    public UserEntity createUser(UserEntity userEntity) {
        CreateSubscriptionResponse subscription = notificationService.createSubscription(userEntity.getEmail());
        userEntity.setSubscriptionId(subscription.getSubscription().getId());
        return repository.save(userEntity);
    }

    @Transactional
    public void deleteUser(Long userId) {
        repository.findById(userId)
                .ifPresent(user -> {
                    notificationService.deleteSubscription(user.getSubscriptionId());
                    repository.deleteById(userId);
                });
    }
}
