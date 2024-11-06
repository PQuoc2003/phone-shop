package com.example.phonecommerce.service;

import com.example.phonecommerce.models.Cart;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {

    Cart getCartById(Long id);

    void deleteCartById(Long id);

    void saveCart(Cart cart);

    void updateCart(Cart cart);

    List<Cart> getAllCarts();

    List<Cart> getCartsByUserId(Long userId);

}
