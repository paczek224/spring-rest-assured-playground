package com.paczek.demoRest.products;

import com.paczek.demoRest.util.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

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
    public ProductEntity getProduct(Long id){
        return repo.getReferenceById(id);
    }
}
