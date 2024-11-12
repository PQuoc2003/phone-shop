package com.tdtu.phonecommerce.service;

import com.tdtu.phonecommerce.models.Orders;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrdersService {

    List<Orders> getAllOrder();

    void addOrder(Orders order);

    void deleteById(Long id);

    void updateStatus(Long id);

    Orders getOrdersById(Long id);

    Orders getByCartId(Long cartId);

    void deleteByCartId(Long cartId);

    List<Orders> getByUsernameAndStatus(String username, String status);


}
