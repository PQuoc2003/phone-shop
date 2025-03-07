package com.tdtu.phonecommerce.controller;


import com.tdtu.phonecommerce.models.User;
import com.tdtu.phonecommerce.service.CartService;
import com.tdtu.phonecommerce.service.UserService;
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

        boolean hasManagerRole = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(r -> r.getAuthority().equals("ROLES_MANAGER"));

        if (user == null) return "redirect:/login";

        Long userId = user.getId();
        model.addAttribute("userId", userId);
        model.addAttribute("hasManagerRole", hasManagerRole);
        return "cart";
    }


}
