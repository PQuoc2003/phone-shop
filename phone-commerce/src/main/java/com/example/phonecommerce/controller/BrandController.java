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

    @GetMapping(value = {"/manager/brand"})
    public String Brands(Model model) {
        List<Brand> brands = brandService.getAllBrand();
        model.addAttribute("brands", brands);
        return "manager_template/manager_brand";
    }

    @GetMapping(value = {"/manager/brand/add"})
    public String addBrandPage(Model model) {
        model.addAttribute("brand", new Brand());
        return "manager_template/manager_add-brand";
    }

    @PostMapping(value = {"/manager/brand/add"})
    public String addBrandProcess(@ModelAttribute("brand") Brand brand) {
        brandService.addBrand(brand);
        return "redirect:/manager/brand";
    }

    @GetMapping("/manager/brand/edit/{id}")
    public String editBrandPage(@PathVariable("id") Long id, Model model) {
        Brand brand = brandService.getBrandById(id);
        model.addAttribute("brand", brand);
        return "manager_template/manager_edit-brand";
    }

    @PostMapping("/manager/brand/edit/{id}")
    public String editBrandProcess(@ModelAttribute("brand") Brand updatedBrand) {
        brandService.updateBrand(updatedBrand);
        return "redirect:/manager/brand";
    }

    @GetMapping("/manager/brand/delete/{id}")
    public String deleteBrand(@PathVariable Long id) {
        brandService.deleteBrandById(id);
        return "redirect:/manager/brand";
    }


}
