package com.pharma.app.product.playload;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OperationResponse {
    private Boolean success;
    private String message;
}
