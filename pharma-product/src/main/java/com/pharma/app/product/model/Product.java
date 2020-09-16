package com.pharma.app.product.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Rico Fauchard
 * @date: 16-09-2020
 */

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "validity_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date validity_date;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getValidity_date() {
        return validity_date;
    }

    public void setValidity_date(Date validity_date) {
        this.validity_date = validity_date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
