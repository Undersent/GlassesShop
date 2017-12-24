package com.example.Glasses.web.controllers.authorization;

import com.example.Glasses.persistence.model.Cart;
import com.example.Glasses.persistence.model.User;
import com.example.Glasses.persistence.model.UserDetails;
import com.example.Glasses.persistence.model.VerificationToken;
import com.example.Glasses.persistence.repositories.CartRepository;
import com.example.Glasses.persistence.repositories.VerificationTokenRepository;
import com.example.Glasses.persistence.services.UserService;
import com.example.Glasses.registration.OnRegistrationCompleteEvent;
import com.example.Glasses.web.dto.UserDto;
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

@RequestMapping(value = "/registration")
@AllArgsConstructor
@RestController
public class RegisterController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private UserService userService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @PostMapping
    public ResponseEntity<?> registerUserAccount(
            @RequestBody @Valid UserDto userDto, HttpServletRequest request){


        User account = createUser(userDto);

        LOGGER.debug("Registering user account with information {}", account);
        User registered = userService.createUserAccount(account);

        String appUrl = "http://" + request.getServerName() + ":"
                + request.getServerPort() + request.getContextPath();

        eventPublisher.publishEvent(
                new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl));

        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    private User createUser(UserDto userDto) {
                return User.builder()
                        .email(userDto.getEmail())
                        .password(userDto.getPassword())
                        .matchingPassword(userDto.getMatchingPassword())
                        .userDetails(
                                UserDetails.builder()
                                        .firstName(userDto.getFirstName())
                                        .lastName(userDto.getLastName())
                                        .username(userDto.getUserName())
                                        .build()
                        )
                        .build();
    }

    @GetMapping(value = "/confirm")
    public ResponseEntity<?> confirmRegistration(@RequestParam("token") final String token) {
        final String result = userService.validateVerificationToken(token);
        if (result.equals("TOKEN_VALID")) {
            //final User user = userService.getUserByToken(token);
            createCartForUser(token);
            return new ResponseEntity("User confirmed", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity("Token not found", HttpStatus.NOT_FOUND);

    }

    private void createCartForUser(String token) {
        VerificationToken verToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Error with creating cart"));
        Cart cart = Cart.builder()
                .userId(verToken.getUser()
                        .getUserId())
                .build();

        cartRepository.save(cart);
        verToken.getUser().getUserDetails().setCart(cart);
        userService.saveUser(verToken.getUser());
    }

}
