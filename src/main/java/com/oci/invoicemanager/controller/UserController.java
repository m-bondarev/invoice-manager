package com.oci.invoicemanager.controller;

import com.oci.invoicemanager.data.UserEntity;
import com.oci.invoicemanager.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/users", produces = APPLICATION_JSON_VALUE)
public class UserController {
    private final UsersService usersService;

    @GetMapping
    public List<UserEntity> getAll() {
        return usersService.getAll();
    }

    @PostMapping
    public UserEntity create(@RequestBody UserEntity user) {
        return usersService.createUser(user);
    }

    @DeleteMapping(value = "/{userId}")
    public void delete(@PathVariable Long userId) {
        usersService.deleteUser(userId);
    }
}
