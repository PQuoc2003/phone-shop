package com.tdtu.phonecommerce.service.imp;

import com.tdtu.phonecommerce.models.Colors;
import com.tdtu.phonecommerce.repository.ColorsRepository;
import com.tdtu.phonecommerce.service.ColorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ColorsServiceImp implements ColorsService {


    private final ColorsRepository colorsRepository;

    @Autowired
    public ColorsServiceImp(ColorsRepository colorsRepository) {
        this.colorsRepository = colorsRepository;
    }

    @Override
    public List<Colors> getAllColor() {
        return colorsRepository.findAll();
    }

    @Override
    public void addColors(Colors colors) {
        colorsRepository.save(colors);
    }

    @Override
    public Colors getColorById(Long id) {

        Optional<Colors> colors = colorsRepository.findById(id);

        return colors.orElse(null);

    }

    @Override
    public void updateColors(Colors colors) {

        colorsRepository.save(colors);

    }

    @Override
    public void deleteColors(Long id) {
        colorsRepository.deleteById(id);
    }
}
