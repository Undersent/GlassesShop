package com.example.Glasses.persistence.services;

import com.example.Glasses.persistence.model.User;
import com.example.Glasses.persistence.model.VerificationToken;
import com.example.Glasses.persistence.repositories.RoleRepository;
import com.example.Glasses.persistence.repositories.UserRepository;
import com.example.Glasses.persistence.repositories.VerificationTokenRepository;
import com.example.Glasses.web.exception.TokenException;
import com.example.Glasses.web.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @_(@Autowired))
@NoArgsConstructor
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    private VerificationTokenRepository tokenRepository;
    private RoleRepository roleRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByUserId(int id) {
        return userRepository.findByUserId(id);
    }

    @Override
    public User getUserByToken(String token) {
            return tokenRepository.findByToken(token)
                    .orElseThrow(() -> new UserException(1,token))
                    .getUser();
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

    @Override
    public void createVerificationTokenForUser(User user, String token) {
        VerificationToken myToken = VerificationToken.builder()
                .token(token)
                .user(user)
                .build();
        myToken.updateToken(token);
        tokenRepository.save(myToken);
    }

    @Override
    public User createUserAccount(User account) {
        this.validateUserByEmail(account.getEmail());
        account.setRoles(new HashSet<>(Collections
                .singletonList(roleRepository
                        .findByRole("ROLE_USER"))));

        return userRepository.save(account);
    }

    @Override
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenException(token));

        final User user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            tokenRepository.delete(verificationToken);
            return "TOKEN_EXPIRED";
        }

        user.setEnabled(true);
        userRepository.save(user);
        return "TOKEN_VALID";
    }

    private void validateUserByEmail(String email) {
        this.userRepository
                .findByEmail(email)
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .ifPresent(u -> {
                    throw new UserException("user with that email exists");
                });
    }
}
