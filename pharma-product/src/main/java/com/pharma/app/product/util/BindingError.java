package com.pharma.app.product.util;

import lombok.Data;

@Data
public class BindingError {

    private String objectName;
    private String fieldName;
    private String fieldValue;
    private String errorMessage;

    public BindingError() {
        this.objectName = "";
        this.fieldName = "";
        this.fieldValue = "";
        this.errorMessage = "";
    }

    public BindingError(String objectName, String fieldName, String fieldValue, String errorMessage) {
        this.objectName = objectName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.errorMessage = errorMessage;
    }
}
