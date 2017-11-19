package com.example.Glasses.web.controllers.authorization;

import com.example.Glasses.persistence.model.User;
import com.example.Glasses.persistence.services.UserService;
import com.example.Glasses.registration.OnRegistrationCompleteEvent;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequestMapping(value = "user/registration")
@AllArgsConstructor(onConstructor = @_(@Autowired))
@RestController
public class RegisterController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private ApplicationEventPublisher eventPublisher;
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> registerUserAccount(
            @RequestBody @Valid User account, HttpServletRequest request){

        LOGGER.debug("Registering user account with information {}", account);
        User registered = userService.createUserAccount(account);

        String appUrl = "http://" + request.getServerName() + ":"
                + request.getServerPort() + request.getContextPath();

        eventPublisher.publishEvent(
                new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl));

        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/confirm")
    public ResponseEntity<?> confirmRegistration(@RequestParam("token") final String token) {
        final String result = userService.validateVerificationToken(token);
        if (result.equals("TOKEN_VALID")) {
            //final User user = userService.getUserByToken(token);
            return new ResponseEntity("User confirmed", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity("Token not found", HttpStatus.NOT_FOUND);

    }

}
