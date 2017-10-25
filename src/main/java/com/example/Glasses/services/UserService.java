package com.example.Glasses.services;

import com.example.Glasses.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
    Optional<User> findByUserId(int id);
    Page<User> findAll(Pageable pageable);
    User saveUser(User user);
    User UpdateStaffById(User user);
}
