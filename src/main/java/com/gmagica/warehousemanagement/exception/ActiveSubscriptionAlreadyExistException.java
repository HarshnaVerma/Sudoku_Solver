package com.gmagica.warehousemanagement.exception;

public class ActiveSubscriptionAlreadyExistException extends PayconException {



    public ActiveSubscriptionAlreadyExistException(String mobileNumber) {
        super(mobileNumber);
        setParamNames("mobileNumber");
    }
}
