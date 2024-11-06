package com.example.phonecommerce.service;

import com.example.phonecommerce.models.User;

import java.util.List;

public interface UserService{

    User findByUsername(String username);

    List<User> findByEmail(String email);

    User findById(Long id);

    User saveUser(User user);

}
