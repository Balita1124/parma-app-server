package com.pharma.app.product.repository;

import com.pharma.app.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT m FROM Product m WHERE LOWER(m.name) LIKE LOWER(concat('%',?1, '%')) or LOWER(m.code) LIKE LOWER(concat('%', ?1, '%'))")
    Page<Product> findByNameLikeOrCodeLike(String keyword, Pageable pageable);

    Product findByCode(String code);

    Product findByName(String name);

    @Query("SELECT m FROM Product m WHERE m.id not in (?2) and m.code = ?1")
    Product findByCodeAndId(String code, Integer id);

    @Query("SELECT m FROM Product m WHERE m.id not in (?2) and m.name = ?1")
    Product findByNameAndId(String name, Integer id);
}
