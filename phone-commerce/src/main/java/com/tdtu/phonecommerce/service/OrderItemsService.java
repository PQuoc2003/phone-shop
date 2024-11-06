package com.tdtu.phonecommerce.service;

import com.tdtu.phonecommerce.models.OrderItems;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderItemsService {

    OrderItems getOrderItemById(Long id);

    void deleteOrderItemById(Long id);

    void saveOrderItems(OrderItems orderItems);

    void updateOrderItems(OrderItems orderItems);

    List<OrderItems> getAllOrderItems();



}
