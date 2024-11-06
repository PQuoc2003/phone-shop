package com.example.phonecommerce.api;

import com.example.phonecommerce.dto.ProductDTO;
import com.example.phonecommerce.models.Product;
import com.example.phonecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductAPI {

    private final ProductService productService;


    @Autowired
    public ProductAPI(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/api/products/all")
    public Page<ProductDTO> getAllProduct(Pageable pageable) {
        Page<Product> products = productService.getAllProduct(pageable);
        return (products.map(productService::convertToDTO));
    }

    @GetMapping("/api/products/filter")
    public List<ProductDTO> filterProducts(
            @RequestParam(name = "productName", required = false, defaultValue = "") String productName,
            @RequestParam(name = "color", required = false, defaultValue = "") String color,
            @RequestParam(name = "category", required = false, defaultValue = "") String category,
            @RequestParam(name = "brand", required = false, defaultValue = "") String brand,
            @RequestParam(name = "minPrice", required = false, defaultValue = "0") int minPrice,
            @RequestParam(name = "maxPrice", required = false, defaultValue = "999999999") int maxPrice) {
        List<Product> products = productService.search(category, productName, brand, minPrice, maxPrice, color);
        List<ProductDTO> productDTOS = new ArrayList<>();

        for (Product product : products) {
            productDTOS.add(productService.convertToDTO(product));
        }

        return productDTOS;


    }

    @GetMapping("/api/products/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {

        Product product = productService.getProductById(id);

        if (product == null) return ResponseEntity.notFound().build();

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setBrand(product.getBrand() != null ? product.getBrand().getName() : "N/A");
        productDTO.setColor(product.getColor() != null ? product.getColor().getName() : "N/A");
        productDTO.setDescription(product.getDescription() != null ? product.getDescription() : "N/A");
        productDTO.setCategory(product.getCategory() != null ? product.getCategory().getName() : "N/A");
        productDTO.setPicture(product.getPicture());
        productDTO.setPrice(product.getPrice());

        return ResponseEntity.ok().body(productDTO);


    }


}
