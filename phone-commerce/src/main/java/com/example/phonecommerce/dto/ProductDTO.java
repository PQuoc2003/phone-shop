package com.example.phonecommerce.dto;

import com.example.phonecommerce.models.Product;
import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    private String name;

    private long price;

    private String description;

    private String picture;

    private String brand;

    private String color;

    private String category;


    public void convertToDTO(Product product){

        this.id = product.getId();
        this.name = product.getName();
        this.brand = product.getBrand() != null ? product.getBrand().getName() : "N/A";
        this.color = product.getColor() != null ? product.getColor().getName() : "N/A";
        this.description = product.getDescription() != null ? product.getDescription() : "N/A";
        this.category = product.getCategory() != null ? product.getCategory().getName(): "N/A";
        this.picture = product.getPicture();
        this.price = product.getPrice();
    }



}

