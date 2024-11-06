package com.tdtu.phonecommerce.service;

import com.tdtu.phonecommerce.models.CartItems;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartItemsService {

    CartItems getCartItemById(Long id);

    void deleteCartItemById(Long id);

    void save(CartItems cartItems);

    List<CartItems> getCartItemByCartId(Long id);


}
