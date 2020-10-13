package com.gmagica.warehousemanagement.exception;


import org.springframework.http.ResponseEntity;

import java.lang.reflect.UndeclaredThrowableException;

public interface CommonExceptionHandler {
    ResponseEntity<PayconExceptionDetails> handlePayconException(PayconException payconException);
    ResponseEntity<PayconExceptionDetails> handleUnknownException(Exception exception);
    ResponseEntity<PayconExceptionDetails> handleUnhandledException(UndeclaredThrowableException exception);
}
