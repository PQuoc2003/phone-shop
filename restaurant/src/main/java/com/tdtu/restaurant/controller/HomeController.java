package com.tdtu.restaurant.controller;


import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {


    @GetMapping("/api/home")
    public String getHomepage(){
        return "Hello from backend";
    }




}
