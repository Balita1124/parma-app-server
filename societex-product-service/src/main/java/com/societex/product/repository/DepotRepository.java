package com.societex.product.repository;

import com.societex.product.model.Depot;
import com.societex.product.model.Depot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DepotRepository extends JpaRepository<Depot, Integer> {
    @Query("SELECT m FROM Depot m WHERE LOWER(m.name) LIKE LOWER(concat('%',?1, '%')) or LOWER(m.code) LIKE LOWER(concat('%', ?1, '%'))")
    Page<Depot> findByNameLikeOrCodeLike(String keyword, Pageable pageable);

    Depot findByCode(String code);

    Depot findByName(String name);

    @Query("SELECT m FROM Depot m WHERE m.id not in (?2) and m.code = ?1")
    Depot findByCodeAndId(String code, Integer id);

    @Query("SELECT m FROM Depot m WHERE m.id not in (?2) and m.name = ?1")
    Depot findByNameAndId(String name, Integer id);
}
