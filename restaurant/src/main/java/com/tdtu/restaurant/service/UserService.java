package com.tdtu.restaurant.service;

import com.tdtu.restaurant.models.MyUser;

public interface UserService {

    MyUser findByUsername(String username);

}
