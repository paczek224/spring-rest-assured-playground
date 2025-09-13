package com.paczek.demoRest.products;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paczek.demoRest.util.Mappers;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;
    final ObjectMapper ob = new ObjectMapper();

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> products(@RequestParam(value = "name", required = false) String name,
                                                     @RequestParam(value = "description", required = false) String description
    ) {
        return new ResponseEntity<>(productService.findByNameAndLikeDescription(name, description)
                .stream()
                .map(Mappers::map)
                .toList(), HttpStatusCode.valueOf(200));
    }
}
