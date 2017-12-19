package com.example.Glasses.persistence.services;

import com.example.Glasses.persistence.model.User;
import com.example.Glasses.persistence.model.UserItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface UserService {
    Optional<User> findByEmail(String email);
    Optional<User> findByUserId(int id);
    List<UserItem> findAllUserItems(int id);
    User getUserByToken(String token);
    Page<User> findAll(Pageable pageable);
    User saveUser(User user);
    User UpdateStaffById(User user);
    void createVerificationTokenForUser(User user, String token);
    void addItemToUserCart(int userId, int itemId);
    User createUserAccount(User account);

    String validateVerificationToken(String token);
}
