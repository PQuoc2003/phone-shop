package com.example.phonecommerce.controller;

import com.example.phonecommerce.dto.OrdersDTO;
import com.example.phonecommerce.models.Orders;
import com.example.phonecommerce.models.User;
import com.example.phonecommerce.service.OrdersService;
import com.example.phonecommerce.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {

    private final OrdersService ordersService;

    private final UserService userService;


    @Autowired
    public OrderController(OrdersService ordersService, UserService userService) {
        this.ordersService = ordersService;
        this.userService = userService;
    }


    @GetMapping("/admin/orders")
    public String getAdminOrderPage(Model model) {

        List<Orders> orders = ordersService.getAllOrder();

        List<OrdersDTO> ordersDTOS = new ArrayList<>();

        for (Orders order : orders) {
            OrdersDTO ordersDTO = new OrdersDTO();
            ordersDTO.convertToDTO(order);
            ordersDTOS.add(ordersDTO);
        }

        model.addAttribute("orders", ordersDTOS);

        return "admin_template/admin_orders";


    }

    @GetMapping("/admin/orders/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        ordersService.deleteById(id);
        return "redirect:/admin/orders";
    }

    @GetMapping("/admin/orders/edit/{id}")
    public String updateStatus(@PathVariable Long id) {
        ordersService.updateStatus(id);
        return "redirect:/admin/orders";
    }

    @GetMapping(value = "/orders-page")
    public String orderPage(@RequestParam("vnp_ResponseCode") String responseCode, Model model, HttpSession session) {
        if (responseCode.equals("00")) {
            // Lấy orderDetails từ session
            OrdersDTO ordersDTO = (OrdersDTO) session.getAttribute("orderDetails");

            // Đưa orderDetails vào model để sử dụng trong view
            model.addAttribute("orderDetails", ordersDTO);

            Orders orders = new Orders();
            User user = userService.findById(ordersDTO.getUserID());

            if (user == null) return "redirect:/home";

            orders.setUserOrder(user);
            orders.setCreateDate(ordersDTO.getOrderCreated());
            orders.setOrder_total(ordersDTO.getTotal());
            orders.setOrder_status("Chưa Giao Hàng");


        }

        return "orders-details";

    }


}
