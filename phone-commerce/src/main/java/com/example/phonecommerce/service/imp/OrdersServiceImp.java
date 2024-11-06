package com.example.phonecommerce.service.imp;

import com.example.phonecommerce.models.Orders;
import com.example.phonecommerce.repository.OrdersRepository;
import com.example.phonecommerce.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersServiceImp implements OrdersService {

    private final OrdersRepository ordersRepository;

    @Autowired
    public OrdersServiceImp(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @Override
    public List<Orders> getAllOrder() {
        return ordersRepository.findAll();
    }

    @Override
    public void addOrder(Orders order) {
        ordersRepository.save(order);
    }

    @Override
    public void deleteById(Long id) {
        ordersRepository.deleteById(id);
    }

    @Override
    public void updateStatus(Long id) {
        Orders order = ordersRepository.findById(id).get();
        order.setOrder_status("Đang Giao Hàng");
        ordersRepository.save(order);

    }
}
