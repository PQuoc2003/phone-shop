package com.example.phonecommerce.controller;

import com.example.phonecommerce.dto.ProductDTO;
import com.example.phonecommerce.models.Product;
import com.example.phonecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductDetailsController {

    private final ProductService productService;


    @Autowired
    public ProductDetailsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/details")
    public String getProductDetails(@RequestParam Long id, Model model){
        Product product = productService.getProductById(id);

        if(product == null) return "redirect:/home";

        ProductDTO productDTO  = new ProductDTO();

        productDTO.convertToDTO(product);

        model.addAttribute("product", productDTO);

        return "product-details";


    }



}
