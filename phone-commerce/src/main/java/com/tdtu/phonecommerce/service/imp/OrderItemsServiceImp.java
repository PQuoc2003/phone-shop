package com.tdtu.phonecommerce.service.imp;

import com.tdtu.phonecommerce.models.OrderItems;
import com.tdtu.phonecommerce.repository.OrderItemsRepository;
import com.tdtu.phonecommerce.service.OrderItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemsServiceImp implements OrderItemsService {


    private final OrderItemsRepository orderItemsRepository;

    @Autowired
    public OrderItemsServiceImp(OrderItemsRepository orderItemsRepository) {
        this.orderItemsRepository = orderItemsRepository;
    }

    @Override
    public OrderItems getOrderItemById(Long id) {
        Optional<OrderItems> orderItems = orderItemsRepository.findById(id);
        return orderItems.orElse(null);
    }

    @Override
    public void deleteOrderItemById(Long id) {
        orderItemsRepository.deleteById(id);

    }

    @Override
    public void saveOrderItems(OrderItems orderItems) {
        orderItemsRepository.save(orderItems);

    }

    @Override
    public void updateOrderItems(OrderItems orderItems) {
        orderItemsRepository.save(orderItems);
    }

    @Override
    public List<OrderItems> getAllOrderItems() {
        return orderItemsRepository.findAll();
    }
}
