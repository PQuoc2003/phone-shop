package com.tdtu.phonecommerce.service;

import com.tdtu.phonecommerce.models.Roles;
import com.tdtu.phonecommerce.models.User;

import java.util.List;

public interface UserService{

    User findByUsername(String username);

    List<User> findByEmail(String email);

    User findById(Long id);

    User saveUser(User user);

    List<User> findByRoles(Roles roles);

    void deleteById(Long id);

}
