package com.example.Glasses.web.controllers.authorization;

import com.example.Glasses.persistence.model.User;
import com.example.Glasses.persistence.services.UserService;
import com.example.Glasses.registration.OnRegistrationCompleteEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping(value = "user/registration")
@RestController
public class RegisterController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private ApplicationEventPublisher eventPublisher;
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> registerUserAccount(
            User account, HttpServletRequest request){

        LOGGER.debug("Registering user account with information {}", account);
        User registered = userService.createUserAccount(account);

        String appUrl = "http://" + request.getServerName() + ":"
                + request.getServerPort() + request.getContextPath();

        eventPublisher.publishEvent(
                new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl));

        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

}