package com.example.phonecommerce.api;

import com.example.phonecommerce.dto.CartItemsDTO;
import com.example.phonecommerce.dto.OrdersDTO;
import com.example.phonecommerce.models.Cart;
import com.example.phonecommerce.models.CartItems;
import com.example.phonecommerce.models.Orders;
import com.example.phonecommerce.models.User;
import com.example.phonecommerce.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class CartAPI {


    private final CartItemsService cartItemsService;

    private final UserService userService;

    private final ProductService productService;

    private final CartService cartService;

    private final OrdersService ordersService;

    @Autowired
    public CartAPI(CartItemsService cartItemsService,
                   UserService userService,
                   ProductService productService,
                   CartService cartService,
                   OrdersService ordersService) {
        this.cartItemsService = cartItemsService;
        this.userService = userService;
        this.productService = productService;
        this.cartService = cartService;
        this.ordersService = ordersService;
    }

    /***
     * @param cartItemDTO list of items of one cart
     * @param session
     * This module receive request and save items to database
     */
    @PostMapping("api/cart/save")
    public void saveCart(@RequestBody List<CartItemsDTO> cartItemDTO, HttpSession session) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User user = userService.findByUsername(username);

        if (user == null) return;

        Cart cart = new Cart();

        cart.setUser(user);

        cartService.saveCart(cart);

        int total = 0;
        int quantity = 0;


        for (CartItemsDTO cartItemsDTO : cartItemDTO) {

            CartItems cartItems = new CartItems();

            cartItems.setCart(cart);

            cartItems.setProduct(productService.getProductById(cartItemsDTO.getId()));

            cartItems.setQuantity(cartItemsDTO.getQuantity());

            cartItemsService.save(cartItems);

            total += cartItemsDTO.getQuantity() * cartItemsDTO.getPrice();
            quantity += cartItemsDTO.getQuantity();

        }


        OrdersDTO ordersDTO = new OrdersDTO();

        ordersDTO.setUserID(user.getId());
        ordersDTO.setOrderCreated(LocalDateTime.now());
        ordersDTO.setEmail(user.getEmail() != null ? user.getEmail() : "N/A");
        ordersDTO.setTotal(total);
        ordersDTO.setQuantity(quantity);
        ordersDTO.setAddress(user.getAddress());
        ordersDTO.setFullName(user.getName());
        ordersDTO.setPhoneNumber(user.getPhoneNumber());
        session.setAttribute("orderDetails", ordersDTO);


        Orders orders = new Orders();
        orders.setUserOrder(user);
        orders.setCreateDate(ordersDTO.getOrderCreated());
        orders.setOrder_total(ordersDTO.getTotal());
        orders.setOrder_status("Chưa Giao Hàng");
        ordersService.addOrder(orders);


    }


}
