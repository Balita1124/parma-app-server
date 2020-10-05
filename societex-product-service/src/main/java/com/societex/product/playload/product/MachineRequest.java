package com.societex.product.playload.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class MachineRequest {
    @NotBlank(message = "Nom est obligatoire")
    private String name;

    @NotBlank(message = "Code est obligatoire")
    private String code;

    @NotNull(message = "Le prix de vente est obligatoire")
    private BigDecimal priceUnit;

    @NotNull
    private BigDecimal pricePurchaseAverage;

    @NotBlank
    private String productType;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull
    private Date validityDate;

    private Double width;

    private Double weight;

    private Double length;

    private Double watt;

    public MachineRequest() {
    }
}
