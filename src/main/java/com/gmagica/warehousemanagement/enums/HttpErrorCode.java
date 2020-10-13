package com.gmagica.warehousemanagement.enums;

public enum HttpErrorCode{

    /**
     * Http error code indicating request data incorrect / mismatched
     */
    HTTP_BAD_REQUEST(400),

    /**
     * Http error code indicating missing authentication
     */
    HTTP_UNAUTHORIZED(401),

    /**
     * Http error code indicating not enough privileges for the operation
     */
    HTTP_FORBIDDEN(403),

    /**
     * Http error code indicating entity not found for requested parameters
     */
    HTTP_NOT_FOUND(404),

    /**
     * Http error code indicating entity already exist in system
     */

    HTTP_CONFLICT(409),
    /**
     * Http error code indicating entity not found for requested parameters
     */
    HTTP_SESSION_EXPIRED(440),

    /**
     * Http error code indicating that request failed because it depended on another request and that request failed
     */
    HTTP_FAILED_DEPENDENCY(424),

    /**
     * Http error code indicating server-side error.
     */
    HTTP_INTERNAL_SERVER_ERROR(500),

    /**
     * Http error code indicating function is not functioning temporarily
     */
    HTTP_SERVICE_UNAVAILABLE(503),

    /**
     * Http error code indicating Precondition failed
     */

    HTTP_PRECONDITION_FAILED(412);


    private int code;

    HttpErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
