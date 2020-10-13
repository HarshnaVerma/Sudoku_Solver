package com.gmagica.warehousemanagement.exception;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PayconExceptionDetails {

    @SerializedName("errors")
    @Expose
    private List<PayconError> errors = new ArrayList<>();

    public List<PayconError> getErrors() {
        return errors;
    }

    public void setErrors(List<PayconError> errors) {
        this.errors = errors;
    }

}
