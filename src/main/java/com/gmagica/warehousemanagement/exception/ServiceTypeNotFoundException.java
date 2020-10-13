package com.gmagica.warehousemanagement.exception;

public class ServiceTypeNotFoundException extends PayconException {

    public ServiceTypeNotFoundException(String serviceId) {

        super(serviceId); //Constructor
        setParamNames("serviceId");
        setField(serviceId);
    }
}
