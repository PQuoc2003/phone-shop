package com.example.phonecommerce.service.imp;

import com.example.phonecommerce.models.CartItems;
import com.example.phonecommerce.repository.CartItemsRepository;
import com.example.phonecommerce.service.CartItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemsServiceImp implements CartItemsService {


    private final CartItemsRepository cartItemsRepository;


    @Autowired
    public CartItemsServiceImp(CartItemsRepository cartItemsRepository) {
        this.cartItemsRepository = cartItemsRepository;
    }

    @Override
    public CartItems getCartItemById(Long id) {
        return cartItemsRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteCartItemById(Long id) {
        cartItemsRepository.deleteById(id);
    }

    @Override
    public void save(CartItems cartItems) {
        cartItemsRepository.save(cartItems);

    }

    @Override
    public List<CartItems> getCartItemByCartId(Long id) {
        return cartItemsRepository.findByCartId(id);
    }
}
