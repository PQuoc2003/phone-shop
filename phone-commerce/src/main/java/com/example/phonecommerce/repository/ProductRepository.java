package com.example.phonecommerce.repository;

import com.example.phonecommerce.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p " +
            "WHERE " +
            "(:category IS NULL OR p.category.name LIKE :category) " +
            "AND (:colors IS NULL OR p.color.name LIKE :colors) " +
            "AND (:brand IS NULL OR p.brand.name LIKE :brand) " +
            "AND (:name IS NULL OR p.name LIKE CONCAT('%', :name, '%')) " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice)")
    List<Product> searchByManyCondition(@Param("category") String category,
                                         @Param("name") String name,
                                         @Param("brand") String brand,
                                         @Param("colors") String colors,
                                         @Param("minPrice") int minPrice,
                                         @Param("maxPrice") int maxPrice);


    @Query("SELECT p FROM Product p JOIN p.category c WHERE c.id = :categoryId")
    List<Product> findAllByCategoryId(@Param("categoryId") Long categoryId);

    Page<Product> findAll(Pageable pageable);









}
