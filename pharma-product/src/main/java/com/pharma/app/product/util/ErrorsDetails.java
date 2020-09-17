package com.pharma.app.product.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ErrorsDetails {

    private List<BindingError> bindingErrors = new ArrayList<BindingError>();

    public List<BindingError> getBindingErrors() {
        return bindingErrors;
    }

    public void setBindingErrors(List<BindingError> bindingErrors) {
        this.bindingErrors = bindingErrors;
    }

    public void addError(BindingError bindingError) {
        this.bindingErrors.add(bindingError);
    }

    public void addAllErrors(BindingResult bindingResult) {
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            BindingError error = new BindingError();
            error.setObjectName(fieldError.getObjectName());
            error.setFieldName(fieldError.getField());
            error.setFieldValue(fieldError.getRejectedValue().toString());
            error.setErrorMessage(fieldError.getDefaultMessage());
            addError(error);
        }
    }

    public String toJSON() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        String errorsAsJSON = "";
        try {
            errorsAsJSON = mapper.writeValueAsString(bindingErrors);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return errorsAsJSON;
    }

    @Override
    public String toString() {
        return "ErrorsDetails [bindingErrors=" + bindingErrors + "]";
    }
}
