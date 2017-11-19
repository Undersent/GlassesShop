package com.example.Glasses.persistence.services;

import com.example.Glasses.persistence.model.Role;
import com.example.Glasses.persistence.repositories.UserRepository;
import com.example.Glasses.web.exception.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class AuthenticationService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            if(email == null ||  email.isEmpty()){
                throw new UsernameNotFoundException("User not found");
            }
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found");
        }

            return toUserDetails(userRepository.findByEmail(email)
                    .orElseThrow(() -> new UserException(email)));

    }

    private UserDetails toUserDetails(com.example.Glasses.persistence.model.User userObject) {
        return org.springframework.security.core.userdetails.User.withUsername(userObject.getEmail())
                .password(userObject.getPassword())
                .authorities(getAuthorities(userObject)).build();
    }

    private List<GrantedAuthority> getAuthorities(com.example.Glasses.persistence.model.User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : user.getRoles()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRole());
            authorities.add(grantedAuthority);
        }
        return authorities;
    }
}
