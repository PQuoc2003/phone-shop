package com.example.phonecommerce.api;

import com.example.phonecommerce.dto.BrandDTO;
import com.example.phonecommerce.models.Brand;
import com.example.phonecommerce.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BrandAPI {

    private final BrandService brandService;

    @Autowired
    public BrandAPI(BrandService brandService) {
        this.brandService = brandService;
    }

    @RequestMapping("/api/brands")
    public List<BrandDTO> getAllBrand(){
        List<Brand> brands =  brandService.getAllBrand();
        List<BrandDTO> brandDTOS = new ArrayList<>();
        brands.forEach(brand -> {
            if (brand.getName() != null) {
                BrandDTO brandDTO = new BrandDTO();
                brandDTO.setId(brand.getId());
                brandDTO.setName(brand.getName());
                brandDTOS.add(brandDTO);
            }
        });
        return  brandDTOS;

    }


}
