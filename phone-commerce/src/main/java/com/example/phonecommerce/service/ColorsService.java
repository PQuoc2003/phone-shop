package com.example.phonecommerce.service;

import com.example.phonecommerce.models.Colors;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ColorsService {

    List<Colors> getAllColor();

    void addColors(Colors colors);

    Colors getColorById(Long id);

    void updateColors(Colors colors);

    void deleteColors(Long id);

}
