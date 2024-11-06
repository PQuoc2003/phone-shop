package com.example.phonecommerce.service;

import com.example.phonecommerce.models.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    List<Category> getAllCategory();

    void addCategory(Category category);

    Category getCategoryById(Long id);

    void updateCategory(Category category);

    void deleteCategory(Long id);


}
