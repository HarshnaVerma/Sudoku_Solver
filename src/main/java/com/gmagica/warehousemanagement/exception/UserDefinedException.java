package com.gmagica.warehousemanagement.exception;

public class UserDefinedException extends RuntimeException {


    public UserDefinedException() {
        super();
    }

    public UserDefinedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserDefinedException(final String message) {
        super(message);
    }

    public UserDefinedException(final Throwable cause) {
        super(cause);
    }


}
