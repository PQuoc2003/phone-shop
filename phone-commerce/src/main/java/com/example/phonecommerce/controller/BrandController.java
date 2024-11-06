package com.example.phonecommerce.controller;

import com.example.phonecommerce.models.Brand;
import com.example.phonecommerce.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BrandController {

    private final BrandService brandService;


    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping(value = {"/admin/brand"})
    public String Brands(Model model) {
        List<Brand> brands = brandService.getAllBrand();
        model.addAttribute("brands", brands);
        return "admin_template/admin_brand";
    }

    @GetMapping(value = {"/admin/brand/add"})
    public String addBrandPage(Model model) {
        model.addAttribute("brand", new Brand());
        return "admin_template/admin_add-brand";
    }

    @PostMapping(value = {"/admin/brand/add"})
    public String addBrandProcess(@ModelAttribute("brand") Brand brand) {
        brandService.addBrand(brand);
        return "redirect:/admin/brand";
    }

    @GetMapping("/admin/brand/edit/{id}")
    public String editBrandPage(@PathVariable("id") Long id, Model model) {
        Brand brand = brandService.getBrandById(id);
        model.addAttribute("brand", brand);
        return "admin_template/admin_edit-brand";
    }

    @PostMapping("/admin/brand/edit/{id}")
    public String editBrandProcess(@ModelAttribute("brand") Brand updatedBrand) {
        brandService.updateBrand(updatedBrand);
        return "redirect:/admin/brand";
    }

    @GetMapping("/admin/brand/delete/{id}")
    public String deleteBrand(@PathVariable Long id) {
        brandService.deleteBrandById(id);
        return "redirect:/admin/brand";
    }


}
