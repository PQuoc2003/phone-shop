package com.example.phonecommerce.controller;

import com.example.phonecommerce.models.Roles;
import com.example.phonecommerce.models.User;
import com.example.phonecommerce.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Slf4j
@Controller
@AllArgsConstructor
public class AuthController {


    private final UserService userService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping(value = {"/", "/login"})
    public String loginForm(Model model, User user) {
        model.addAttribute("user", user);
        return "Login";
    }

    @GetMapping(value = {"/register"})
    public String registerForm(Model model) {
        model.addAttribute("newUser", new User());
        return "Register";
    }

    @PostMapping("/register")
    public String registerForm(@ModelAttribute("newUser") User newUser, Model model) {
        String password = newUser.getPassword();

        newUser.setPassword(bCryptPasswordEncoder.encode(password));

        newUser.setRoles(Roles.ROLES_USER);


        List<User> checkedUsers = userService.findByEmail(newUser.getEmail());

        if (!checkedUsers.isEmpty()) {
            model.addAttribute("errorRegister", "Đăng ký không thành công, email đã tồn tại");
            return "redirect:/register";
        }


        try {
            userService.saveUser(newUser);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("errorRegister", "Đăng ký không thành công, email đã tồn tại");
            return "redirect:/register";
        }


    }


}
