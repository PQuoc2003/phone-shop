package com.tdtu.phonecommerce.controller;


import com.tdtu.phonecommerce.dto.ProductDTO;
import com.tdtu.phonecommerce.models.Brand;
import com.tdtu.phonecommerce.models.Category;
import com.tdtu.phonecommerce.models.Colors;
import com.tdtu.phonecommerce.models.Product;
import com.tdtu.phonecommerce.service.BrandService;
import com.tdtu.phonecommerce.service.CategoryService;
import com.tdtu.phonecommerce.service.ColorsService;
import com.tdtu.phonecommerce.service.ProductService;
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

    @GetMapping(value = "/manager/product")
    public String productAdminPage(Model model) {
        List<Product> productList = productService.getAllProducts();
        model.addAttribute("products", productList);
        return "manager_template/manager_product";
    }

    @GetMapping(value = {"/manager/product/add"})
    public String addProductPage(Model model) {
        List<Category> categories = categoryService.getAllCategory();
        List<Brand> brands = brandService.getAllBrand();
        List<Colors> colors = colorsService.getAllColor();
        Product product = new Product();
        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);
        model.addAttribute("colors", colors);
        model.addAttribute("product", product);
        return "manager_template/manager_add-products";
    }

    @PostMapping(value = {"/manager/product/add"})
    public String addProduct(@ModelAttribute("product") Product product,
                             @RequestParam("image") MultipartFile image) {


        if (image.getOriginalFilename() == null) return "redirect: /manager/product/add";

        String sourceDirectory = "src/main/resources/static";
        String uploadDirectory = "/image";
        String finalDirectory = sourceDirectory + uploadDirectory;


        String times = String.valueOf(Instant.now().getEpochSecond());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        if (username.isEmpty())
            return "redirect:/login";


        String[] nameList = image.getOriginalFilename().split("\\.");

        if (nameList.length < 2) return "redirect:/manager/product/add";

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

        return "redirect:/manager/product";
    }

    @GetMapping(value = "/manager/product/edit/{id}")
    public String getEditProductPage(@PathVariable("id") Long id, Model model) {

        Product product = productService.getProductById(id);

        List<Brand> brands = brandService.getAllBrand();
        List<Category> categories = categoryService.getAllCategory();
        List<Colors> colors = colorsService.getAllColor();


        model.addAttribute("brands", brands);
        model.addAttribute("categories", categories);
        model.addAttribute("colors", colors);
        model.addAttribute("product", product);

        return "manager_template/manager_edit-product";
    }


    @PostMapping(value = "/manager/product/edit/{id}")
    public String editProductProcess(@ModelAttribute("product") Product updatedProduct,
                                     @RequestParam("image") MultipartFile image) {

        if(image == null){
            Product product = productService.getProductById(updatedProduct.getId());
            updatedProduct.setPicture(product.getPicture());
            productService.updateProduct(updatedProduct);
            return "redirect:/manager/product";

        }

        if (image.getOriginalFilename() == null) return "redirect: /manager/product/add";

        String sourceDirectory = "src/main/resources/static";
        String uploadDirectory = "/image";
        String finalDirectory = sourceDirectory + uploadDirectory;


        String times = String.valueOf(Instant.now().getEpochSecond());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        if (username.isEmpty())
            return "redirect:/login";


        String[] nameList = image.getOriginalFilename().split("\\.");

        if (nameList.length < 2) return "redirect:/manager/product/add";

        String fileExtension = nameList[nameList.length - 1];

        String fileName = username + times + "." + fileExtension;

        Path path = Paths.get(finalDirectory, fileName);


        try {

            Files.write(path, image.getBytes());

        } catch (Exception e) {
            System.out.println("Error when upload file");
        }


        updatedProduct.setPicture(fileName);

        productService.updateProduct(updatedProduct);

        return "redirect:/manager/product";

    }

    @GetMapping("/manager/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {

        productService.removeById(id);

        return "redirect:/manager/product";
    }

    @GetMapping("/manager/product/detail/{id}")
    public String getProductDetailPage(@PathVariable("id") Long id, Model model) {

        Product product = productService.getProductById(id);

        if (product == null) return "redirect:/manager/product";

        ProductDTO productDTO = new ProductDTO();

        productDTO.convertToDTO(product);

        model.addAttribute("product", productDTO);

        return "manager_template/manager_product-details";
    }


    @GetMapping(value = {"/employee/product", "/employee"})
    public String getEmployeeProductPage(Model model) {

        List<Product> productList = productService.getAllProducts();
        model.addAttribute("products", productList);

        return "employee_template/employee_product";

    }


    @GetMapping(value = "/employee/product/edit/{id}")
    public String getEmployeeEditProductPage(@PathVariable("id") Long id, Model model) {

        Product product = productService.getProductById(id);
        model.addAttribute("product", product);

        return "employee_template/employee_edit-product";
    }

    @PostMapping(value = "/employee/product/edit/{id}")
    public String editQuantity(@ModelAttribute("product") Product updatedProduct) {


        Product product = productService.getProductById(updatedProduct.getId());

        String picture = product.getPicture();

        updatedProduct.setPicture(picture);

        productService.updateProduct(updatedProduct);

        return "redirect:/employee/product";

    }



}
