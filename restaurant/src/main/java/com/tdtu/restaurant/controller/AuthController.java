package com.tdtu.restaurant.controller;


import com.tdtu.restaurant.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {

        if (loginRequest.getUsername().equals("phuquoc")) {
            if (loginRequest.getPassword().equals("123456")) {
                return ResponseEntity.ok("Login Success");
            }
        }


        return ResponseEntity.ok("Login Success");
    }


}
