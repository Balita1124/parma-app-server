package com.societex.product.playload.product;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DepotRequest {
    @NotBlank(message = "Nom est obligatoire")
    private String name;

    @NotBlank(message = "Code est obligatoire")
    private String code;

    @NotBlank(message = "Adresse est obligatoire")
    private String adresse;

    public DepotRequest() {
    }
}
