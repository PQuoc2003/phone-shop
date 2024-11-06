package com.example.phonecommerce.repository;

import com.example.phonecommerce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String username);

    List<User> findByEmail(String email);


}
