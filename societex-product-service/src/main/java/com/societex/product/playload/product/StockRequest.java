package com.societex.product.playload.product;

import com.societex.product.model.Depot;
import com.societex.product.model.Machine;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class StockRequest {
    @NotBlank(message = "Le depot est obligatoire")
    private Depot depot;

    @NotBlank(message = "L'article est obligatoire")
    private Machine machine;

    @NotBlank(message = "La quantité est obligqtoire")
    private Double quantite;

    private Integer mini;

    private Integer max;

    private Integer reppro;

    public StockRequest() {
    }
}
