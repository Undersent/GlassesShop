package com.example.Glasses.persistence.model;

import com.example.Glasses.validators.ExtendedEmailValidator;
import com.example.Glasses.validators.PasswordMatches;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@PasswordMatches
@EqualsAndHashCode
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int userId;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @ExtendedEmailValidator
    @Column(name = "email")
    private String email;

    //@JsonIgnore
    @Column(name = "password")
    private String password;

    //@JsonIgnore
    @Column(name = "matching_password")
    private String matchingPassword;

    @JsonIgnore
    @Column(name = "enabled")
    @Builder.Default
    private boolean enabled = false;

//    @Column(name = "confirmation_email_token")
//    String confirmationEmailToken;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="userDetails")
    private UserDetails userDetails;

}

