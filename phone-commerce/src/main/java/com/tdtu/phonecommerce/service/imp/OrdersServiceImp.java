package com.tdtu.phonecommerce.service.imp;

import com.tdtu.phonecommerce.models.Orders;
import com.tdtu.phonecommerce.repository.OrdersRepository;
import com.tdtu.phonecommerce.service.OrdersService;
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
        Orders order = ordersRepository.findById(id).orElse(null);
        if(order == null) return;
        order.setOrder_status("Đã Giao Hàng");
        ordersRepository.save(order);

    }

    @Override
    public Orders getOrdersById(Long id) {
        return ordersRepository.findById(id).orElse(null);
    }

    @Override
    public Orders getByCartId(Long cartId) {
        return ordersRepository.findByCartId(cartId);
    }

    @Override
    public void deleteByCartId(Long cartId) {
        ordersRepository.deleteByCartId(cartId);
    }

    @Override
    public Orders getByUsernameAndStatus(String username, String status) {
        return ordersRepository.findByUserNameAndStatus(username, status);
    }
}
