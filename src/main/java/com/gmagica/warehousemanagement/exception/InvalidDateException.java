package com.gmagica.warehousemanagement.exception;

public class InvalidDateException extends PayconException {
    public InvalidDateException(String message) {
        super(message);
        setParamNames("message");
    }
}
