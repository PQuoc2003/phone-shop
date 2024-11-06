package com.example.phonecommerce.repository;

import com.example.phonecommerce.models.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Long> {

    @Query("SELECT ci FROM CartItems ci WHERE ci.cart.id = :cartId")
    List<CartItems> findByCartId(Long cartId);


}
