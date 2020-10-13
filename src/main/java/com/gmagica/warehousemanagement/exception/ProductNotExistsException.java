package com.gmagica.warehousemanagement.exception;

public class ProductNotExistsException extends PayconException {

    public ProductNotExistsException(String productName){
        super(productName);
        setParamNames("productNotFound");
        setField(productName);
    }
}
