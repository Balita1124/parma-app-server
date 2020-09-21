package com.pharma.app.product.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "PRODUCTS")
@Data
public class Product extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank
    @Column(unique = true)
    private String name;

    @NotBlank
    @Column(unique = true)
    private String code;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull
    private Date validityDate;

    @NotNull
    private BigDecimal price;

    public Product(@NotBlank String name, @NotBlank String code, @NotNull Date validityDate, @NotNull BigDecimal price) {
        this.name = name;
        this.code = code;
        this.validityDate = validityDate;
        this.price = price;
    }

    public Product() {
    }
}
