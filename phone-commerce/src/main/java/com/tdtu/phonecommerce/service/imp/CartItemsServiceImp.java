package com.tdtu.phonecommerce.service.imp;

import com.tdtu.phonecommerce.dto.CartItemsDTO;
import com.tdtu.phonecommerce.models.CartItems;
import com.tdtu.phonecommerce.models.Product;
import com.tdtu.phonecommerce.repository.CartItemsRepository;
import com.tdtu.phonecommerce.service.CartItemsService;
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
    public void deleteByCartId(Long id) {
        cartItemsRepository.deleteByCartId(id);
    }

    @Override
    public void save(CartItems cartItems) {
        cartItemsRepository.save(cartItems);

    }

    @Override
    public List<CartItems> getCartItemByCartId(Long id) {
        return cartItemsRepository.findByCartId(id);
    }

    @Override
    public CartItemsDTO convertToDTO(CartItems cartItems) {

        CartItemsDTO cartItemsDTO = new CartItemsDTO();

        Product product = cartItems.getProduct();

        if(product == null) return  cartItemsDTO;


        cartItemsDTO.setId(product.getId());
        cartItemsDTO.setPicture(product.getPicture());
        cartItemsDTO.setName(product.getName());
        cartItemsDTO.setDetailProduct(product.getDescription());
        cartItemsDTO.setPrice(product.getPrice());
        cartItemsDTO.setQuantity(cartItems.getQuantity());
        cartItemsDTO.setBrand(product.getBrandName());

        cartItemsDTO.setColor(product.getColor().getName());

        return cartItemsDTO;
    }

}
