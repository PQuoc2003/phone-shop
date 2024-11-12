package com.tdtu.phonecommerce.service.imp;

import com.tdtu.phonecommerce.models.Brand;
import com.tdtu.phonecommerce.repository.BrandRepository;
import com.tdtu.phonecommerce.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImp implements BrandService {

    private final BrandRepository brandRepository;


    @Autowired
    public BrandServiceImp(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public List<Brand> getAllBrand() {
        return brandRepository.findAll();
    }

    @Override
    public Brand getBrandById(Long id) {

        Optional<Brand> brands = brandRepository.findById(id);

        return brands.orElse(null);
    }

    @Override
    public void addBrand(Brand brand) {
        brandRepository.save(brand);
    }

    @Override
    public void updateBrand(Brand brand) {
        brandRepository.save(brand);
    }

    @Override
    public void deleteBrandById(Long id) {
        brandRepository.deleteById(id);
    }
}
