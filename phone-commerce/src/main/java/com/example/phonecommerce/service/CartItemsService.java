package com.example.phonecommerce.service;

import com.example.phonecommerce.models.CartItems;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartItemsService {

    CartItems getCartItemById(Long id);

    void deleteCartItemById(Long id);

    void save(CartItems cartItems);

    List<CartItems> getCartItemByCartId(Long id);


}
