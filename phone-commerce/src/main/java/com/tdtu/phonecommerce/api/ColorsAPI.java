package com.tdtu.phonecommerce.api;

import com.tdtu.phonecommerce.dto.ColorsDTO;
import com.tdtu.phonecommerce.models.Colors;
import com.tdtu.phonecommerce.service.ColorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ColorsAPI {

    private final ColorsService colorsService;

    @Autowired
    public ColorsAPI(ColorsService colorsService) {
        this.colorsService = colorsService;
    }

    @RequestMapping("/api/colors")
    public List<ColorsDTO> getAllColor() {
        List<Colors> colors = colorsService.getAllColor();

        List<ColorsDTO> colorsDTOS = new ArrayList<>();
        colors.forEach(color -> {
            ColorsDTO colorDTO = new ColorsDTO();
            colorDTO.setId(color.getId());
            colorDTO.setName(color.getName());
            colorsDTOS.add(colorDTO);
        });

        return colorsDTOS;

    }


}
