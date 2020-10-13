package com.gmagica.warehousemanagement.exception;

public class FeignUnknownException extends PayconException {
    public FeignUnknownException(String mobileNumber) {
        super(mobileNumber);
        setParamNames("mobileNumber");
    }

}
