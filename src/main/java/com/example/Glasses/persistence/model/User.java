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
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int userId;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @ExtendedEmailValidator
    @Column(name = "email")
    private String email;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @JsonIgnore
    @Column(name = "matching_password")
    private String matchingPassword;

    @JsonIgnore
    @Column(name = "enabled")
    @Builder.Default
    private boolean enabled = false;

    @Column(name = "confirmation_email_token")
    String confirmationEmailToken;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="userDetails")
    private UserDetails userDetails;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (getUserId() != user.getUserId()) return false;
        if (isEnabled() != user.isEnabled()) return false;
        if (getRoles() != null ? !getRoles().equals(user.getRoles()) : user.getRoles() != null) return false;
        if (getEmail() != null ? !getEmail().equals(user.getEmail()) : user.getEmail() != null) return false;
        if (getPassword() != null ? !getPassword().equals(user.getPassword()) : user.getPassword() != null)
            return false;
        if (getConfirmationEmailToken() != null ? !getConfirmationEmailToken().equals(user.getConfirmationEmailToken()) : user.getConfirmationEmailToken() != null)
            return false;
        return getUserDetails() != null ? getUserDetails().equals(user.getUserDetails()) : user.getUserDetails() == null;
    }

    @Override
    public int hashCode() {
        int result = getUserId();
        result = 31 * result + (getRoles() != null ? getRoles().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (isEnabled() ? 1 : 0);
        result = 31 * result + (getConfirmationEmailToken() != null ? getConfirmationEmailToken().hashCode() : 0);
        result = 31 * result + (getUserDetails() != null ? getUserDetails().hashCode() : 0);
        return result;
    }
}

