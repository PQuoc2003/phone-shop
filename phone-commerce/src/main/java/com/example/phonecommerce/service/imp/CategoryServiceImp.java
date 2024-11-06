package com.example.phonecommerce.service.imp;

import com.example.phonecommerce.models.Category;
import com.example.phonecommerce.repository.CategoryRepository;
import com.example.phonecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements CategoryService {

    private final CategoryRepository categoryRepository;


    @Autowired
    public CategoryServiceImp(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public void addCategory(Category category) {
        categoryRepository.save(category);

    }

    @Override
    public Category getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);

        return category.orElse(null);
    }

    @Override
    public void updateCategory(Category category) {
        categoryRepository.save(category);

    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
