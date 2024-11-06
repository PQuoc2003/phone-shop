package com.example.phonecommerce.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value = {"/home", "/homepage"})
    public String homePage(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        boolean hasAdminRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLES_ADMIN"));

        model.addAttribute("hasAdminRole", hasAdminRole);


        return "homepage";
    }

    @GetMapping(value = "/403")
    public String forbidPage() {
        return "403";
    }

    @GetMapping(value = "/admin")
    public String adminHome() {
        return "admin_template/admin_home";
    }





}
