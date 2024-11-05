package com.tdtu.restaurant.service.imp;

import com.tdtu.restaurant.models.MyUser;
import com.tdtu.restaurant.repository.UserRepository;
import com.tdtu.restaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;



    @Autowired
    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public MyUser findByUsername(String username) {
        return userRepository.findByUserName(username);

    }

}
