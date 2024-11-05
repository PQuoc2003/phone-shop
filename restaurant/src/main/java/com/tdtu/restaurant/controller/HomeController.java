package com.tdtu.restaurant.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {


    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public HomeController(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/api/home")
    public String getHomepage(){
        return "Hello from backend";
    }


    @PostMapping("test/generate-password")
    public String genPassword(){
        return passwordEncoder.encode("phuquoc");
    }





}
