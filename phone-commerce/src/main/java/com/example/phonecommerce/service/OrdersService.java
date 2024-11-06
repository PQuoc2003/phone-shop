package com.example.phonecommerce.service;

import com.example.phonecommerce.models.Orders;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrdersService {

    List<Orders> getAllOrder();

    void addOrder(Orders order);

    void deleteById(Long id);

    void updateStatus(Long id);


}
