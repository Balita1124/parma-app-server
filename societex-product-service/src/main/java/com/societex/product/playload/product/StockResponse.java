package com.societex.product.playload.product;

import com.societex.product.model.Stock;
import com.societex.product.playload.PageResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class StockResponse extends PageResponse {
    @ApiModelProperty(required = true, value = "")
    private List<Stock> items;
}
