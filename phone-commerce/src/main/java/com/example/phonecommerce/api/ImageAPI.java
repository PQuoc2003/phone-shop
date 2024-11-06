package com.example.phonecommerce.api;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ImageAPI {

    @GetMapping(value = "/api/images/{picture}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public @ResponseBody Resource getImage(@PathVariable String picture) {
        return new ClassPathResource("static/image/" +  picture );
    }
}
