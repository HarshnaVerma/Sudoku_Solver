package com.gmagica.warehousemanagement.exception;




import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.gmagica.warehousemanagement.dto.ResponseEntityParam;

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private ReloadableResourceBundleMessageSource messageSource;

    @Autowired
    public GlobalExceptionHandler(ReloadableResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
    @ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> ResourceNotFoundHandling(ResourceNotFoundException exception, WebRequest request){
		ErrorInfo errorDetails = new ErrorInfo(new Date(0), exception.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

    
    @ExceptionHandler(value = {UserDefinedException.class})
    protected ResponseEntity<Object> handleNotFound(final RuntimeException ex, final WebRequest request) {
        final String bodyOfResponse = "This should be application specific";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {PayconException.class})
    public ResponseEntity<?> handlePayconException(PayconException ex, final WebRequest request) {
        ResponseEntityParam errors = new ResponseEntityParam();

        ErrorAggangement errorAggangement = PayconExceptionDecoder.getErrorArrgement(ex);
        String errorMessage = messageSource.getMessage(errorAggangement.messageTemplate, ex.getParams(), ex.getLocale());
        errors.setMessage(errorMessage);
        errors.setField(ex.getField());
        errors.setCode(errorAggangement.payconErrorCode.getPayconErrorCode());
        System.out.println("exception Handler:" + errorMessage);
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.valueOf(errorAggangement.httpErrorCode.getCode()), request);
    }

}
