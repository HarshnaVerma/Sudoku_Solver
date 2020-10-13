package com.gmagica.warehousemanagement.exception;


public class SubscriberNotFoundException extends PayconException {


    public SubscriberNotFoundException(String mobileNumber) {
        super(mobileNumber);
        setParamNames("mobileNumber");
    }
}
