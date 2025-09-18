package com.paczek.demo.app.products;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paczek.demo.app.util.Mappers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

import static com.paczek.demo.app.util.Mappers.map;

@RestController
public class ProductController {

    private RestTemplate restTemplate;
    private String currencyUrl;
    private final ProductService productService;
    final ObjectMapper ob = new ObjectMapper();

    public ProductController(ProductService productService, RestTemplate restTemplate, @Value("${exchange.rate.url}") String currencyUrl) {
        this.productService = productService;
        this.restTemplate = restTemplate;
        this.currencyUrl = currencyUrl;
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


    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id,
                                                 @RequestParam(value = "currency", required = false) String currency) {

        if (Objects.isNull(currency)) {
            return ResponseEntity.ok(map(productService.getProduct(id)));
        } else {
            CurrencyRateResponse currencies = getCurrencies(currency);
            return ResponseEntity.ok(map(productService.getProduct(id), currency, currencies.rates().get(0).mid()));
        }
    }

    @PostMapping("/products")
    public ResponseEntity<String> addProduct(@RequestBody ProductDto productDto) {
        try {
            return new ResponseEntity<>(ob.writeValueAsString(productService.save(productDto)), HttpStatusCode.valueOf(201));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/currency")
    public CurrencyRateResponse getCurrencies(@RequestParam(name = "symbols") String currency) {
        return restTemplate.getForObject(currencyUrl, CurrencyRateResponse.class, currency);
    }
}
