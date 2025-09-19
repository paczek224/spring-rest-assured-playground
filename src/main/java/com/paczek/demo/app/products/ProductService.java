package com.paczek.demo.app.products;

import com.paczek.demo.app.util.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductService {
    private final ProductRepository repo;

    public ProductService(ProductRepository repo){
        this.repo=repo;
    }

    public List<ProductEntity> getAllProducts(){
        return repo.findAll();
    }

    public List<ProductEntity>findByNameAndLikeDescription(String name, String description){
        return repo.findByNameAndDescriptionOptional(name,description);
    }

    public ProductDto save(ProductEntity productEntity){
        return Mappers.map(repo.save(productEntity));
    }

    public ProductDto save(ProductDto productDto){
        return save(Mappers.map(productDto));
    }

    public Optional<ProductEntity> getProduct(Long id){
        return repo.findById(id);
    }
}
