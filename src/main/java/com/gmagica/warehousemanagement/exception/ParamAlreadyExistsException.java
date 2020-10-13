package com.gmagica.warehousemanagement.exception;

public class ParamAlreadyExistsException extends PayconException {

    public ParamAlreadyExistsException(String paramExists){
        super(paramExists);
        setParamNames("paramExists");
    }

}
