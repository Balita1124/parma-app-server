package com.pharma.app.product.repository;

import com.pharma.app.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    public List<Product> findAll();

    public Page<Product> findAll(Pageable p);

    @Query("SELECT m FROM Product m WHERE LOWER(m.name) LIKE LOWER(concat('%',?1, '%')) or LOWER(m.code) LIKE LOWER(concat('%', ?1, '%'))")
    public Page<Product> findByNameLikeOrCodeLike(String key, Pageable p);

    Optional<Product> findOneById(Integer id);

    Product findFirstByCode(String code);

    Product findFirstByName(String name);
}
