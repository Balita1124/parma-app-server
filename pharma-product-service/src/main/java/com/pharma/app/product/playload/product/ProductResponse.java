package com.pharma.app.product.playload.product;

import com.pharma.app.product.model.Product;
import com.pharma.app.product.playload.PageResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductResponse extends PageResponse {
    @ApiModelProperty(required = true, value = "")
    private List<Product> items;
}
