package com.example.phonecommerce.controller;


import com.example.phonecommerce.models.Colors;
import com.example.phonecommerce.service.ColorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ColorsController {

    private final ColorsService colorsService;

    @Autowired
    public ColorsController(ColorsService colorsService) {
        this.colorsService = colorsService;
    }

    @GetMapping(value = {"/admin/color"})
    public String Color_Admin(Model model) {

        List<Colors> colors = colorsService.getAllColor();

        model.addAttribute("colors", colors);
        return "admin_template/admin_colors";
    }

    @GetMapping(value = {"/admin/color/add"})
    public String addColors(Model model) {
        model.addAttribute("colors", new Colors());
        return "admin_template/admin_add-colors"; // Đặt tên template hiển thị form thêm thương hiệu
    }

    @PostMapping(value = {"/admin/color/add"})
    public String addColorsProcess(@ModelAttribute("colors") Colors colors) {
        colorsService.addColors(colors);
        return "redirect:/admin/color";
    }

    @GetMapping("/admin/color/edit/{id}")
    public String editColorPage(@PathVariable("id") Long id, Model model) {
        Colors colors = colorsService.getColorById(id);
        model.addAttribute("colors", colors);
        return "admin_template/admin_edit-colors";
    }

    @PostMapping("/admin/color/edit/{id}")
    public String editColorProcess(@ModelAttribute("colors") Colors updateColors) {
        colorsService.updateColors(updateColors);
        return "redirect:/admin/color";
    }

    @GetMapping("/admin/color/delete/{id}")
    public String deleteColor(@PathVariable Long id) {
        colorsService.deleteColors(id);
        return "redirect:/admin/color";
    }


}
