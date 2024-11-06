package com.tdtu.phonecommerce.service.imp;

import com.tdtu.phonecommerce.models.Cart;
import com.tdtu.phonecommerce.repository.CartRepository;
import com.tdtu.phonecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImp implements CartService {


    private final CartRepository cartRepository;

    @Autowired
    public CartServiceImp(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart getCartById(Long id) {
        Optional<Cart> cart = cartRepository.findById(id);
        return cart.orElse(null);
    }

    @Override
    public void deleteCartById(Long id) {

        cartRepository.deleteById(id);
    }

    @Override
    public void saveCart(Cart cart) {
        cartRepository.save(cart);

    }

    @Override
    public void updateCart(Cart cart) {
        cartRepository.save(cart);

    }

    @Override
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    @Override
    public List<Cart> getCartsByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
}
