package com.tdtu.phonecommerce.repository;

import com.tdtu.phonecommerce.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
