package com.tdtu.phonecommerce.dto;

import com.tdtu.phonecommerce.models.CartItems;
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

    public void convertToDTO(CartItems cartItems) {

        this.id = cartItems.getId() != null ? cartItems.getId() : -1L;
        this.picture = cartItems.getProduct() != null ? cartItems.getProduct().getPicture() : "N/A";
        this.name = cartItems.getProduct() != null ? cartItems.getProduct().getName() : "N/A";
        this.price = cartItems.getProduct() != null ? cartItems.getProduct().getPrice() : -1L;
        this.detailProduct = cartItems.getProduct() != null ? cartItems.getProduct().getDescription() : "N/A";
        this.quantity = cartItems.getQuantity();
        this.brand = cartItems.getProduct() != null ? cartItems.getProduct().getBrand().getName() : "N/A";
        this.color = cartItems.getProduct() != null ? cartItems.getProduct().getColor().getName() : "N/A";

    }
}

