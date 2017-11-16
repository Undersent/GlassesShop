package com.example.Glasses.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class TokenException extends RuntimeException {

    public TokenException(String token) {
        super("token is invalid " + token);
    }

}
