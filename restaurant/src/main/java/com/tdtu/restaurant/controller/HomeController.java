package com.tdtu.restaurant.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/api/home")
    public String getHomepage(){
        return "Hello from backend";
    }


}
