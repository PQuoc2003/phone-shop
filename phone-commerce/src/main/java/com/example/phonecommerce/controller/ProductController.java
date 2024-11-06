package com.example.phonecommerce.controller;


import com.example.phonecommerce.dto.ProductDTO;
import com.example.phonecommerce.models.Brand;
import com.example.phonecommerce.models.Category;
import com.example.phonecommerce.models.Colors;
import com.example.phonecommerce.models.Product;
import com.example.phonecommerce.service.BrandService;
import com.example.phonecommerce.service.CategoryService;
import com.example.phonecommerce.service.ColorsService;
import com.example.phonecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;
    private final ColorsService colorsService;
    private final BrandService brandService;
    private final CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, ColorsService colorsService, BrandService brandService, CategoryService categoryService) {
        this.productService = productService;
        this.colorsService = colorsService;
        this.brandService = brandService;
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/admin/product")
    public String productAdminPage(Model model) {
        List<Product> productList = productService.getAllProducts();
        model.addAttribute("products", productList);
        return "admin_template/admin_product";
    }

    @GetMapping(value = {"/admin/product/add"})
    public String addProductPage(Model model) {
        List<Category> categories = categoryService.getAllCategory();
        List<Brand> brands = brandService.getAllBrand();
        List<Colors> colors = colorsService.getAllColor();
        Product product = new Product();
        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);
        model.addAttribute("colors", colors);
        model.addAttribute("product", product);
        return "admin_template/admin_add-products";
    }

    @PostMapping(value = {"/admin/product/add"})
    public String addProduct(@ModelAttribute("product") Product product,
                             @RequestParam("image") MultipartFile image) {


        if(image.getOriginalFilename() == null) return "redirect: /admin/product/add";

        String sourceDirectory = "src/main/resources/static";
        String uploadDirectory = "/image";
        String finalDirectory  = sourceDirectory + uploadDirectory;


        String times = String.valueOf(Instant.now().getEpochSecond());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        if (username.isEmpty())
            return "redirect:/login";


        String[] nameList = image.getOriginalFilename().split("\\.");

        if (nameList.length < 2) return "redirect:/admin/product/add";

        String fileExtension = nameList[nameList.length - 1];

        String fileName = username + times + "." + fileExtension;

        Path path = Paths.get(finalDirectory, fileName);


        try {

            Files.write(path, image.getBytes());

        } catch (Exception e) {
            System.out.println("Error when upload file");
        }


        product.setPicture(fileName);
        productService.addProduct(product);

        return "redirect:/admin/product";
    }

    @GetMapping(value = "/admin/product/edit/{id}")
    public String getEditProductPage(@PathVariable("id") Long id, Model model) {

        Product product = productService.getProductById(id);

        List<Brand> brands = brandService.getAllBrand();
        List<Category> categories = categoryService.getAllCategory();
        List<Colors> colors = colorsService.getAllColor();


        model.addAttribute("brands", brands);
        model.addAttribute("categories", categories);
        model.addAttribute("colors", colors);
        model.addAttribute("product", product);

        return "admin_template/admin_edit-product";
    }


    @PostMapping(value = "/admin/product/edit/{id}")
    public String editProductProcess(@ModelAttribute("product") Product updatedProduct,
                                     @RequestParam("image") String image) {
        updatedProduct.setPicture(image);
        productService.updateProduct(updatedProduct);

        return "redirect:/admin/product";

    }

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {

        productService.removeById(id);

        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/detail/{id}")
    public String getProductDetailPage(@PathVariable("id") Long id, Model model){

        Product product = productService.getProductById(id);

        if (product == null) return "redirect:/admin/product";

        ProductDTO productDTO = new ProductDTO();

        productDTO.convertToDTO(product);

        model.addAttribute("product", productDTO);

        return "admin_template/admin_product-details";
    }


}
