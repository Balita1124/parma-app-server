package com.pharma.app.product.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductRequest {
    private Integer id;
    @NotBlank(message = "Name can't blank")
    private String name;
    @NotBlank(message = "Code can't blank")
    private String code;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date validity_date;
    @NotNull
    private BigDecimal price;
}
