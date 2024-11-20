package com.tdtu.phonecommerce.controller;

import com.tdtu.phonecommerce.dto.OrdersDTO;
import com.tdtu.phonecommerce.models.*;
import com.tdtu.phonecommerce.service.CartItemsService;
import com.tdtu.phonecommerce.service.OrdersService;
import com.tdtu.phonecommerce.service.ProductService;
import com.tdtu.phonecommerce.service.UserService;
import jakarta.persistence.criteria.Order;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {

    private final OrdersService ordersService;

    private final CartItemsService cartItemsService;

    private final ProductService productService;

    private final UserService userService;


    @Autowired
    public OrderController(
            OrdersService ordersService,
            UserService userService,
            CartItemsService cartItemsService,
            ProductService productService
    ) {
        this.ordersService = ordersService;
        this.userService = userService;
        this.cartItemsService = cartItemsService;
        this.productService = productService;
    }

    private List<OrdersDTO> getOrdersDTOByStatus(String status) {

        List<Orders> orders = ordersService.getAllByStatus(status);

        List<OrdersDTO> ordersDTOS = new ArrayList<>();

        for (Orders order : orders) {
            OrdersDTO ordersDTO = new OrdersDTO();
            ordersDTO.convertToDTO(order);
            ordersDTOS.add(ordersDTO);
        }

        return ordersDTOS;
    }

    @GetMapping(value = {"/manager/orders", "/employee/orders"})
    public String getOrderPage(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isManager = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLES_MANAGER"));


//        List<Orders> orders = ordersService.getAllOrder();

        String status = "Chưa giao hàng";


        List<OrdersDTO> ordersDTOS = this.getOrdersDTOByStatus(status);

        model.addAttribute("orders", ordersDTOS);

        if (isManager) {
            return "manager_template/manager_orders";
        }

        return "employee_template/employee_orders";

    }

    @GetMapping(value = {"/manager/orders/{type}", "/employee/orders/{type}"})
    public String getSuccessOrdersPage(@PathVariable String type, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isManager = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLES_MANAGER"));


        String status = "Đã Giao Hàng";

        if (type.equals("cancel")) status = "Đã Hủy";

        List<OrdersDTO> ordersDTOS = this.getOrdersDTOByStatus(status);

        model.addAttribute("orders", ordersDTOS);

        if (isManager) {
            return "manager_template/manager_orders-success";
        }

        return "employee_template/employee_orders-success";


    }

    @GetMapping(value = {"/manager/orders/delete/{id}", "/employee/orders/delete/{id}"})
    public String deleteOrder(@PathVariable Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isManager = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLES_MANAGER"));


        String status = "Đã Hủy";
        ordersService.updateStatus(id, status);

        Orders orders = ordersService.getOrdersById(id);

        Long cartId = orders.getCart().getId();

        List<CartItems> cartItemsList = cartItemsService.getCartItemByCartId(cartId);

        for (CartItems cartItems : cartItemsList) {
            Product product = cartItems.getProduct();
            int quantity = cartItems.getQuantity();
            int currentQuantity = product.getQuantity();
            int newQuantity = currentQuantity + quantity;
            product.setQuantity(newQuantity);
            productService.updateProduct(product);
        }

        if (isManager) return "redirect:/manager/orders";

        return "redirect:/employee/orders";
    }

    @GetMapping(value = {"/manager/orders/edit/{id}", "/employee/orders/edit/{id}"})
    public String updateStatus(@PathVariable Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isManager = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLES_MANAGER"));

        Orders orders = ordersService.getOrdersById(id);

        if (orders == null) {
            if (isManager)
                return "redirect:/manager/orders";
            return "redirect:/employee/orders";
        }


        ordersService.updateStatus(id);

        if (isManager)
            return "redirect:/manager/orders";
        return "redirect:/employee/orders";
    }

    @GetMapping(value = "/orders-page")
    public String orderPage(@RequestParam("vnp_ResponseCode") String responseCode, Model model, HttpSession session) {
        if (responseCode.equals("00")) {
            // Lấy orderDetails từ session
            OrdersDTO ordersDTO = (OrdersDTO) session.getAttribute("orderDetails");

            Long ordersId = (Long) session.getAttribute("ordersId");

            // Orders hiện tại của khách hàng có trạng thái chờ thanh toán
            Orders orders = ordersService.getOrdersById(ordersId);

            // Đưa orderDetails vào model để sử dụng trong view
            model.addAttribute("orderDetails", ordersDTO);

            // Update tình trạng order thành đã thanh toán - chưa giao hàng
            orders.setOrder_status("Chưa Giao Hàng");
            ordersService.addOrder(orders);

            // Giảm số lượng của sản phẩm đi 1
            Long cartId = (Long) session.getAttribute("cartId");

            List<CartItems> cartItems = cartItemsService.getCartItemByCartId(cartId);

            for (CartItems cartItem : cartItems) {
                Product product = cartItem.getProduct();
                // Quantity còn lại = số lượng hiện tại - số lượng của cart
                product.setQuantity(product.getQuantity() - cartItem.getQuantity());
                productService.updateProduct(product);
            }
            // Xóa session cartId
            // Set nó bằng null

            session.setAttribute("cartId", null);


        }

        return "orders-details";

    }


    @GetMapping("/orders/history")
    public String getOrdersHistoryPage(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        List<Orders> ordersList = ordersService.getByUsernameAndStatus(username, "Chưa Giao Hàng");

        ordersList.addAll(ordersService.getByUsernameAndStatus(username, "Đã Giao Hàng"));

        List<OrdersDTO> ordersDTOList = new ArrayList<>();

        for (Orders orders : ordersList) {
            OrdersDTO ordersDTO = new OrdersDTO();
            ordersDTO.convertToDTO(orders);
            ordersDTOList.add(ordersDTO);
        }


        model.addAttribute("orders", ordersDTOList);

        return "orders-history";

    }


}
