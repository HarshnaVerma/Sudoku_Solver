package com.gmagica.warehousemanagement.exception;

public class SubscriberPendingException  extends PayconException {


    public SubscriberPendingException(String mobileNumber) {
        super(mobileNumber);
        setField(mobileNumber);
    }
}