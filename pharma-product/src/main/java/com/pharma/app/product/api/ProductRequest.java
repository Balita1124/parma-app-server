package com.pharma.app.product.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductRequest {
    private Integer id;
    private String name;
    private String code;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date validity_date;
    private BigDecimal price;
}
