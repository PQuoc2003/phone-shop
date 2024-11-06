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

    @GetMapping(value = {"/manager/color"})
    public String Color_Admin(Model model) {

        List<Colors> colors = colorsService.getAllColor();

        model.addAttribute("colors", colors);
        return "manager_template/manager_colors";
    }

    @GetMapping(value = {"/manager/color/add"})
    public String addColors(Model model) {
        model.addAttribute("colors", new Colors());
        return "manager_template/manager_add-colors"; // Đặt tên template hiển thị form thêm thương hiệu
    }

    @PostMapping(value = {"/manager/color/add"})
    public String addColorsProcess(@ModelAttribute("colors") Colors colors) {
        colorsService.addColors(colors);
        return "redirect:/manager/color";
    }

    @GetMapping("/manager/color/edit/{id}")
    public String editColorPage(@PathVariable("id") Long id, Model model) {
        Colors colors = colorsService.getColorById(id);
        model.addAttribute("colors", colors);
        return "manager_template/manager_edit-colors";
    }

    @PostMapping("/manager/color/edit/{id}")
    public String editColorProcess(@ModelAttribute("colors") Colors updateColors) {
        colorsService.updateColors(updateColors);
        return "redirect:/manager/color";
    }

    @GetMapping("/manager/color/delete/{id}")
    public String deleteColor(@PathVariable Long id) {
        colorsService.deleteColors(id);
        return "redirect:/manager/color";
    }


}
