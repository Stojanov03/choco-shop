package com.choco.chocoshop.service;

import com.choco.chocoshop.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User register (User user);
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);
    List<User> findAll();
    User update(User user);
    void delete(User user);
}
