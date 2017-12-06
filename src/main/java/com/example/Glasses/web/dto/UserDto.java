package com.example.Glasses.web.dto;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    String email;
    String password;
    String matchingPassword;
    String firstName;
    String lastName;
    String userName;
}
