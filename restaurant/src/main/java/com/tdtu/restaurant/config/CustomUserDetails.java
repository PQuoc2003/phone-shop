package com.tdtu.restaurant.config;

import com.tdtu.restaurant.dto.LoginRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {



    private final LoginRequest loginRequest;

    public CustomUserDetails(LoginRequest loginRequest) {
        this.loginRequest = loginRequest;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return loginRequest.getPassword();
    }

    @Override
    public String getUsername() {
        return loginRequest.getUsername();
    }
}
