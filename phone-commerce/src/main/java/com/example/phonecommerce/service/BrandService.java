package com.example.phonecommerce.service;

import com.example.phonecommerce.models.Brand;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BrandService {


    List<Brand> getAllBrand();

    Brand getBrandById(Long id);

    void addBrand(Brand brand);

    void updateBrand(Brand brand);

    void deleteBrandById(Long id);


}
