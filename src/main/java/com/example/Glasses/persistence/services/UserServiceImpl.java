package com.example.Glasses.persistence.services;

import com.example.Glasses.persistence.model.*;
import com.example.Glasses.persistence.repositories.*;
import com.example.Glasses.web.dto.UserDto;
import com.example.Glasses.web.exception.TokenException;
import com.example.Glasses.web.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private VerificationTokenRepository tokenRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserItemRepository userItemRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CartRepository cartRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByUserId(int id) {
        return userRepository.findByUserId(id);
    }

    @Override
    public List<UserItem> findAllUserItems(int id) {
        return userItemRepository.findAll()
                .stream()
                .filter(c->c.getCartId() == userRepository.findByUserId(id)
                        .orElseThrow(
                                ()->new RuntimeException("user with that id doesnt exist"))
                        .getUserDetails()
                        .getCart()
                        .getCartId())
                .collect(Collectors.toList());

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
    public void addItemToUserCart(int userId, int itemId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User with that id doesnt exist"));

        userItemRepository.save(
                UserItem.builder()
                        .cartId(user.getUserDetails().getCart().getCartId())
                        .item(itemRepository.findByItemId(itemId).orElseThrow(
                                () -> new RuntimeException("Item with that id doesnt exist")))
                        .build()
                );
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

    @Override
    public boolean deleteItemFromCart(int userId, int itemId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User with that id doesnt exist"));

        UserItem item = userItemRepository.findAll()
                .stream()
                .filter(i -> i.getItem().getItemId() == itemId)
                .findAny()
                .orElseThrow(()->new RuntimeException("UserItem cant be found"));

        userItemRepository.delete(item);

        return true;
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
