package com.example.Glasses.controllers;

import com.example.Glasses.model.User;
import com.example.Glasses.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor(onConstructor = @_(@Autowired))
@RequestMapping("/users")
public class UsersController {

    UserService userService;

    @PostMapping
    public ResponseEntity<?> postUser(@RequestBody User user){
        try {
            Optional.ofNullable(user.getEmail()).orElseThrow(Exception::new);
            validateUser(user.getEmail());
        } catch (Exception e1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        userService.saveUser(user);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }


    @GetMapping
    public Page<User> getUsers(Pageable pageable){
        return userService.findAll(pageable);
    }


    private void validateUser(String email) throws Exception {
        userService.findByEmail(email)
                .filter(u -> u.getEmail().equals(email))
                .ifPresent(u -> {throw new RuntimeException("found entity with that email");});
    }
}
