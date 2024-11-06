package com.example.phonecommerce.repository;

import com.example.phonecommerce.models.Colors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ColorsRepository extends JpaRepository<Colors, Long> {


}
