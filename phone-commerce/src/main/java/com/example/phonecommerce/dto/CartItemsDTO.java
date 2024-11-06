package com.example.phonecommerce.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartItemsDTO {
    private Long id;
    private String picture;
    private String name;
    private int price;
    private String detailProduct;
    private int quantity;
    private String brand;
    private String color;
}

