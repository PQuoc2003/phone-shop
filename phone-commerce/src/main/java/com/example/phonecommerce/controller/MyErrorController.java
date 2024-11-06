package com.example.phonecommerce.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;

public class MyErrorController implements ErrorController {

    @RequestMapping(value = "/error")
    public String getRedirect(){
        return "redirect:/home";
    }

}
