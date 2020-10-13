package com.gmagica.warehousemanagement.exception;


public class TransactionNotFoundException extends PayconException {


    public TransactionNotFoundException(String transactionId) {
        super(transactionId);
        setParamNames("transactionId");
        setField(transactionId);
    }
}
