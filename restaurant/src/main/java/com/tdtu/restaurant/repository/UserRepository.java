package com.tdtu.restaurant.repository;

import com.tdtu.restaurant.models.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Long> {

    MyUser findByUserName(String username);
}
