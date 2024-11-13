package com.tdtu.phonecommerce.service.imp;

import com.tdtu.phonecommerce.models.Roles;
import com.tdtu.phonecommerce.models.User;
import com.tdtu.phonecommerce.repository.UserRepository;
import com.tdtu.phonecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {


    private final UserRepository userRepository;

    @Autowired
    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUserName(username);

    }

    @Override
    public List<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findByRoles(Roles roles) {

        return userRepository.findUsersByRoles(roles);

    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }


}
