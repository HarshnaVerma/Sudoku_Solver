package com.gmagica.warehousemanagement.exception;


import com.gmagica.warehousemanagement.enums.HttpErrorCode;
import com.gmagica.warehousemanagement.enums.PayconErrorCode;

public class ErrorAggangement {

    /**
     * This code is used in http response, serves as HTTP protocol return/error code
     */
    public final HttpErrorCode httpErrorCode ;

    /**
     * This code is used in payload of the response to identify specific scenario

     */
    public final PayconErrorCode payconErrorCode;

    /**
     * Parametrized message used in endpoint HTTP errro code detailed message.
     */
    public final String messageTemplate;

    public ErrorAggangement(HttpErrorCode httpErrorCode, PayconErrorCode payconErrorCode, String messageTemplate) {
        this.messageTemplate = messageTemplate;
        this.httpErrorCode = httpErrorCode;
        this.payconErrorCode = payconErrorCode;
    }
}
