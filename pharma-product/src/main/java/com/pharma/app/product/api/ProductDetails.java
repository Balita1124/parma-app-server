package com.pharma.app.product.api;

import com.pharma.app.product.model.Product;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductDetails {

    private Integer id;
    private String name;
    private String code;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date validity_date;
    private BigDecimal price;

    ProductDetails(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.code = product.getCode();
        this.validity_date = product.getValidity_date();
        this.price = product.getPrice();
    }
}
