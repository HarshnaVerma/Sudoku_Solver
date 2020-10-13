package com.gmagica.warehousemanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends PayconException
{
    public RecordNotFoundException(String exception) {
        super(exception);
    }
}