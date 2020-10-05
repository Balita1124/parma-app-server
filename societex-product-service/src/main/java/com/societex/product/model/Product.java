package com.societex.product.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
@Inheritance
public abstract class Product extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank
    @Column(unique = true)
    private String code;

    @NotBlank
    private String name;

    @NotNull
    private BigDecimal priceUnit;

    @NotNull
    private BigDecimal pricePurchaseAverage;

    @NotBlank
    private String productType;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull
    private Date validityDate;

    public Product() {
        super();
    }
}
