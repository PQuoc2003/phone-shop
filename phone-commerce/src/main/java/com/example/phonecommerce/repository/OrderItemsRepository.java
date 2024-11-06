package com.example.phonecommerce.repository;

import com.example.phonecommerce.models.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {
}
