package com.example.phonecommerce.controller;


import com.example.phonecommerce.models.User;
import com.example.phonecommerce.service.CartService;
import com.example.phonecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {


    private final CartService cartService;
    private final UserService userService;


    @Autowired
    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping(value = {"/cart"})
    public String Cart(Model model) {

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(userName);

        boolean hasAdminRole = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(r -> r.getAuthority().equals("ROLES_ADMIN"));

        if (user == null) return "redirect:/login";

        Long userId = user.getId();
        model.addAttribute("userId", userId);
        model.addAttribute("hasAdminRole", hasAdminRole);
        return "cart";
    }


}
