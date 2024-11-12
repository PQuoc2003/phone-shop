package com.tdtu.phonecommerce.dto;

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
    private Long price;
    private String detailProduct;
    private int quantity;
    private String brand;
    private String color;

}

