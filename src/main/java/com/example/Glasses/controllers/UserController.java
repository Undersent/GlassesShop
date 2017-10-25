package com.example.Glasses.controllers;

import com.example.Glasses.model.User;
import com.example.Glasses.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@AllArgsConstructor(onConstructor = @_(@Autowired))
@RequestMapping("/users/user/{id}")
public class UserController {

    UserService userService;

    @GetMapping
    public Optional<User> getUser(@PathVariable("id") int id){
        return userService.findByUserId(id);
    }
}
