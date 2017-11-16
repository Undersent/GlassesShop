package com.example.Glasses.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserException extends RuntimeException {

    public UserException(String email) {
        super("user with that email exists: " + email);
    }

    public UserException(int number, String token) {
        super("user with that token doesnt exist: " + token);
    }

    public UserException(int id) {
        super("user with that id doesnt exist: " + id);
    }
}
