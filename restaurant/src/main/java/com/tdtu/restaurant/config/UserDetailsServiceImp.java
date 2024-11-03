package com.tdtu.restaurant.config;

import com.tdtu.restaurant.dto.LoginRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImp implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        LoginRequest loginRequest =null;

        if(username.equals("phuquoc")){
            loginRequest = new LoginRequest("phuquoc", "$2a$12$Mc19OLk2yn5CNU8mhFrUS.Q21szCFVDSbbzhoLJippkZK8w5C7ALa");
        }

        if (loginRequest == null) {
            throw new UsernameNotFoundException("Could not find user!!");
        }
        return new CustomUserDetails(loginRequest);

    }
}
