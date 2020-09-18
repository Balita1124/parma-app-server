package com.pharma.app.product.playload.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductRequest {
    @NotBlank(message = "Nom est obligatoire")
    private String name;

    @NotBlank(message = "Code est obligatoire")
    private String code;

//    @NotNull(message = "Date validité est obligatoire")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date validityDate;

    @NotNull(message = "Le prix de vente est obligatoire")
    private BigDecimal price;

//    public ProductRequest(String name,String code, Date validityDate, BigDecimal price) {
//        this.name = name;
//        this.code = code;
//        this.validityDate = validityDate;
//        this.price = price;
//    }
}
