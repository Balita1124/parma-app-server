package com.societex.product.playload.product;

import com.societex.product.model.Product;
import com.societex.product.playload.PageResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductResponse extends PageResponse {
    @ApiModelProperty(required = true, value = "")
    private List<Product> items;
}
