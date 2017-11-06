package com.example.Glasses.persistence.services;

import com.example.Glasses.persistence.model.User;
import com.example.Glasses.persistence.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @_(@Autowired))
@NoArgsConstructor
@Deprecated
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByUserId(int id) {
        return userRepository.findByUserId(id);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User UpdateStaffById(User user) {
        return userRepository.save(user);
    }
}
