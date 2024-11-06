package com.tdtu.phonecommerce.service;

import com.tdtu.phonecommerce.models.User;

import java.util.List;

public interface UserService{

    User findByUsername(String username);

    List<User> findByEmail(String email);

    User findById(Long id);

    User saveUser(User user);

}
