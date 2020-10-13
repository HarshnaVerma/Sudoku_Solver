package com.gmagica.warehousemanagement.exception;

public class InActiveSubscriptionAlreadyExistsException  extends PayconException {



    public InActiveSubscriptionAlreadyExistsException(String mobileNumber) {
        super(mobileNumber);
        setParamNames("mobileNumber");
       /* SubscriberDTO subscriberDTO = new SubscriberDTO();
        subscriberDTO.setMobileNumber(mobileNumber);
        subscriberDTO.setNickName("Test Data");*/


    }
}