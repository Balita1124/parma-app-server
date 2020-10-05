package com.societex.product.playload.product;

import com.societex.product.model.Machine;
import com.societex.product.playload.PageResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class MachineResponse extends PageResponse {
    @ApiModelProperty(required = true, value = "")
    private List<Machine> items;
}
