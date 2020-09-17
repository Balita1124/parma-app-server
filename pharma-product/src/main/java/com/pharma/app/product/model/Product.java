package com.pharma.app.product.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Rico Fauchard
 * @date: 16-09-2020
 */

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "validity_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date validityDate;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public Integer getId() {
        return id;
    }
}
