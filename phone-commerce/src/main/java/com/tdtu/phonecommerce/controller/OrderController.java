package com.tdtu.phonecommerce.controller;

import com.tdtu.phonecommerce.dto.OrdersDTO;
import com.tdtu.phonecommerce.models.CartItems;
import com.tdtu.phonecommerce.models.Orders;
import com.tdtu.phonecommerce.models.Product;
import com.tdtu.phonecommerce.service.CartItemsService;
import com.tdtu.phonecommerce.service.OrdersService;
import com.tdtu.phonecommerce.service.ProductService;
import com.tdtu.phonecommerce.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/manager/orders")
    public String getAdminOrderPage(Model model) {

        List<Orders> orders = ordersService.getAllOrder();

        List<OrdersDTO> ordersDTOS = new ArrayList<>();

        for (Orders order : orders) {
            OrdersDTO ordersDTO = new OrdersDTO();
            ordersDTO.convertToDTO(order);
            ordersDTOS.add(ordersDTO);
        }

        model.addAttribute("orders", ordersDTOS);

        return "manager_template/manager_orders";

    }

    @GetMapping("/manager/orders/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        ordersService.deleteById(id);
        return "redirect:/manager/orders";
    }

    @GetMapping("/manager/orders/edit/{id}")
    public String updateStatus(@PathVariable Long id) {

        Orders orders = ordersService.getOrdersById(id);

        if(orders == null) return "redirect:/manager/orders";


        ordersService.updateStatus(id);
        return "redirect:/manager/orders";
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

            for (CartItems cartItem: cartItems) {
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


}
