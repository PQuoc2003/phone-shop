package com.example.phonecommerce.repository;

import com.example.phonecommerce.models.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
}
