package com.societex.product.repository;

import com.societex.product.model.Machine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MachineRepository extends JpaRepository<Machine, Integer> {
    @Query("SELECT m FROM Machine m WHERE LOWER(m.name) LIKE LOWER(concat('%',?1, '%')) or LOWER(m.code) LIKE LOWER(concat('%', ?1, '%'))")
    Page<Machine> findByNameLikeOrCodeLike(String keyword, Pageable pageable);

    Machine findByCode(String code);

    Machine findByName(String name);

    @Query("SELECT m FROM Machine m WHERE m.id not in (?2) and m.code = ?1")
    Machine findByCodeAndId(String code, Integer id);

    @Query("SELECT m FROM Machine m WHERE m.id not in (?2) and m.name = ?1")
    Machine findByNameAndId(String name, Integer id);
}
