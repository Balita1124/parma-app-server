package com.pharma.app.product.playload;

import com.pharma.app.product.model.Product;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductResponse extends PageResponse {
    @ApiModelProperty(required = true, value = "")
    private List<Product> items;
}
