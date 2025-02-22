package com.tdtu.phonecommerce.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value = {"/home", "/homepage", "/"})
    public String homePage(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        boolean hasManagerRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLES_MANAGER"));

        boolean hasEmployeeRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLES_EMPLOYEE"));

        model.addAttribute("hasManagerRole", hasManagerRole);

        model.addAttribute("hasEmployeeRole", hasEmployeeRole);


        return "homepage";
    }

    @GetMapping(value = "/403")
    public String forbidPage() {
        return "403";
    }

    @GetMapping(value = "/manager")
    public String adminHome() {
        return "manager_template/manager_home";
    }





}
