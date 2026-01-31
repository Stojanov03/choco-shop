package com.choco.chocoshop.service;

import com.choco.chocoshop.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User register (User user);
    Optional<User> findByUsername(String username);
    List<User> findAll();
    User update(User user);
    void delete(User user);
}
