package com.societex.product.playload.product;

import com.societex.product.model.Depot;
import com.societex.product.playload.PageResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class DepotResponse extends PageResponse {
    @ApiModelProperty(required = true, value = "")
    private List<Depot> items;
}
