package com.example.phonecommerce.api;

import com.example.phonecommerce.dto.CategoryDTO;
import com.example.phonecommerce.models.Category;
import com.example.phonecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryAPI {

    private final CategoryService categoryService;

    @Autowired
    public CategoryAPI(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping("/api/categories")
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryService.getAllCategory();

        List<CategoryDTO> categoryDTOS = new ArrayList<>();

        categories.forEach(category -> {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(category.getId());
            categoryDTO.setName(category.getName());
            // Set other properties if needed
            categoryDTOS.add(categoryDTO);
        });

        return  categoryDTOS;
    }
}
