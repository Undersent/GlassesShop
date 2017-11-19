package com.example.Glasses.web.controllers;

import com.example.Glasses.persistence.model.User;
import com.example.Glasses.persistence.predicate.UserPredicateBuilder;
import com.example.Glasses.persistence.repositories.RoleRepository;
import com.example.Glasses.persistence.repositories.UserRepository;
import com.example.Glasses.web.exception.UserException;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.querydsl.core.types.dsl.BooleanExpression;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@AllArgsConstructor(onConstructor = @_(@Autowired))
@RequestMapping("/users")
public class UserController {

    UserRepository userRepository;
    RoleRepository roleRepository;
    ApplicationEventPublisher eventPublisher;

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") int id){
        return userRepository.findByUserId(id).orElseThrow(() -> new UserException(id));
    }



    //http://localhost:8080/users?search=lastName:doe,age>25
//    @GetMapping
//    public Iterable<User> findAllUsers(@RequestParam(value = "search") String search) {
//        UserPredicateBuilder builder = new UserPredicateBuilder();
//        if (search != null) {
//            Pattern pattern = Pattern.compile("(\\w+?)([:<>])(\\w+?),");
//            Matcher matcher = pattern.matcher(search + ",");
//            while (matcher.find()) {
//                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
//            }
//        }
//        BooleanExpression exp = builder.build();
//        return userRepository.findAll(exp);
//    }
    @GetMapping
    public Iterable<User> findAllUsers(@QuerydslPredicate(root = User.class) Predicate predicate) {
        return userRepository.findAll(predicate);
    }

    @GetMapping("/page")
    public Page<User> getUsers(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody @Valid User user){
        this.validateUserByEmail(user.getEmail());
        user.setRoles(new HashSet<>(Collections
                .singletonList(roleRepository
                        .findByRole("ROLE_USER"))));

        userRepository.save(user);

        return ResponseEntity.ok(HttpStatus.ACCEPTED);
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
