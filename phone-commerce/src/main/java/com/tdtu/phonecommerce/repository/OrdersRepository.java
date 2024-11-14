package com.tdtu.phonecommerce.repository;

import com.tdtu.phonecommerce.models.Orders;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    @Query("SELECT ci FROM Orders ci WHERE ci.cart.id = :cartId")
    Orders findByCartId(Long cartId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Orders ci WHERE ci.cart.id = :cartId")
    void deleteByCartId(Long cartId);

    @Query("SELECT ci FROM Orders ci WHERE ci.order_status = :status and ci.userOrder.userName = :username")
    List<Orders> findByUserNameAndStatus(String username, String status);

    @Query("SELECT ci FROM Orders ci WHERE ci.order_status = :status")
    List<Orders> findByStatus(String status);




}
