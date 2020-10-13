package com.gmagica.warehousemanagement.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.reflect.UndeclaredThrowableException;

public abstract class CommonExceptionHandlerImpl implements CommonExceptionHandler {



    @Override
    @ExceptionHandler(value = UndeclaredThrowableException.class)
    public ResponseEntity<PayconExceptionDetails> handleUnhandledException(UndeclaredThrowableException exception) {
        Throwable actualException = exception.getCause();
        if(actualException instanceof PayconException) {
            return handlePayconException((PayconException) actualException);
        }
        else {
            return handleUnknownException((Exception) actualException);
        }

    }
}
