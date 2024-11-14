package com.tdtu.phonecommerce.repository;

import com.tdtu.phonecommerce.models.Roles;
import com.tdtu.phonecommerce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String username);

    List<User> findByEmail(String email);

    List<User> findUsersByRoles(Roles roles);


}
