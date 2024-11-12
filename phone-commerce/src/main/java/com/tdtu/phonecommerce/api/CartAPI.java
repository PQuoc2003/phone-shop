package com.tdtu.phonecommerce.api;

import com.tdtu.phonecommerce.dto.CartItemsDTO;
import com.tdtu.phonecommerce.dto.OrdersDTO;
import com.tdtu.phonecommerce.dto.ProductDTO;
import com.tdtu.phonecommerce.models.*;
import com.tdtu.phonecommerce.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @PostMapping("/api/cart/save")
    public void saveCart(@RequestBody List<CartItemsDTO> cartItemDTO, HttpSession session) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User user = userService.findByUsername(username);

        if (user == null) return;

        Cart cart = new Cart();

        boolean existCart = false;

        // sau bước này sẽ có được cái cart
        if (session.getAttribute("cartId") != null) {
            Long id = (Long) session.getAttribute("cartId");
            cart = cartService.getCartById(id);
            existCart = true;

            // xóa đi hết các cart items của cart đó.
            // Sau bước này cart items null
            cartItemsService.deleteByCartId(id);

        } else {
            cart.setUser(user);
            cartService.saveCart(cart);
        }


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

        // have the total and quantity of cart

        OrdersDTO ordersDTO = new OrdersDTO();

        ordersDTO.setUserID(user.getId());
        ordersDTO.setOrderCreated(LocalDateTime.now());
        ordersDTO.setEmail(user.getEmail() != null ? user.getEmail() : "N/A");
        ordersDTO.setTotal(total);
        ordersDTO.setQuantity(quantity);
        ordersDTO.setAddress(user.getAddress());
        ordersDTO.setFullName(user.getName());
        ordersDTO.setPhoneNumber(user.getPhoneNumber());
        ordersDTO.setCartId(cart.getId());


        // Nếu cart đã tồn tại thì xóa cái order cũ đi
        // Vì đây là hành động tạo 1 cái order mới
        // Thì cái order cũ sẽ bị xóa đi, viết lại bằng cái order mới
        if (existCart) {
            ordersService.deleteByCartId(cart.getId());
        }

        Orders orders = new Orders();
        orders.setUserOrder(user);
        orders.setCreateDate(ordersDTO.getOrderCreated());
        orders.setOrder_total(ordersDTO.getTotal());
        orders.setOrder_status("Chờ Thanh toán");
        orders.setCart(cart);
        ordersService.addOrder(orders);

        session.setAttribute("orderDetails", ordersDTO);
        session.setAttribute("ordersId", orders.getId());
        session.setAttribute("cartId", cart.getId());

    }

    @GetMapping("/api/cart/get")
    ResponseEntity<List<CartItemsDTO>> getCurrentCart() {

        List<CartItemsDTO> cartItemsDTOList = new ArrayList<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        // get current Order (Chờ thanh toán)

        String status = "Chờ Thanh toán";


        List<Orders> ordersList = ordersService.getByUsernameAndStatus(username, status);


        if (ordersList.isEmpty()) return ResponseEntity.ofNullable(cartItemsDTOList);

        Orders orders = ordersList.get(0);

        Long id = orders.getCart().getId();

        List<CartItems> cartItemsList = cartItemsService.getCartItemByCartId(id);


        for (CartItems cartItems : cartItemsList) {

            CartItemsDTO cartItemsDTO = cartItemsService.convertToDTO(cartItems);

            cartItemsDTOList.add(cartItemsDTO);


        }

        return ResponseEntity.ok(cartItemsDTOList);
    }


}
