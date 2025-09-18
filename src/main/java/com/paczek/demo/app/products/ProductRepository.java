package com.paczek.demo.app.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("""
    SELECT p FROM ProductEntity p
      WHERE (:name IS NULL OR LOWER(p.name) = LOWER(:name))
      AND (:description IS NULL OR LOWER(p.description) LIKE CONCAT('%', LOWER(:description), '%'))
    """)
    List<ProductEntity> findByNameAndDescriptionOptional(
            @Param("name") String name,
            @Param("description") String description);
}
