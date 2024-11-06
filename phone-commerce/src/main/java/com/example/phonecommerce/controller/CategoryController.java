package com.example.phonecommerce.controller;

import com.example.phonecommerce.models.Category;
import com.example.phonecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping(value = {"/manager/category"})
    public String Color_Admin(Model model) {

        List<Category> categories = categoryService.getAllCategory();

        model.addAttribute("categories", categories);

        return "manager_template/manager_category";
    }


    @GetMapping(value = {"/manager/category/add"})
    public String addColors(Model model) {
        model.addAttribute("category", new Category());
        return "manager_template/manager_add-category"; // Đặt tên template hiển thị form thêm thương hiệu
    }

    @PostMapping(value = {"/manager/category/add"})
    public String addCategoryProcess(@ModelAttribute("category") Category category) {
        categoryService.addCategory(category);
        return "redirect:/manager/category";
    }

    @GetMapping("/manager/category/edit/{id}")
    public String editCategoryPage(@PathVariable("id") Long id, Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "manager_template/manager_edit-category";
    }

    @PostMapping("/manager/category/edit/{id}")
    public String editBrandProcess(@ModelAttribute("category") Category updatedCategory) {
        categoryService.updateCategory(updatedCategory);
        return "redirect:/manager/category";
    }


    @GetMapping("/manager/category/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/manager/category";
    }




}
