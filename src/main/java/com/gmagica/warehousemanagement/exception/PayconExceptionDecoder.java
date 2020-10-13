package com.gmagica.warehousemanagement.exception;


import static com.gmagica.warehousemanagement.enums.HttpErrorCode.*;
import static com.gmagica.warehousemanagement.enums.PayconErrorCode.*;

import java.util.HashMap;
import java.util.Map;

import com.gmagica.warehousemanagement.enums.HttpErrorCode;
import com.gmagica.warehousemanagement.enums.PayconErrorCode;


public class PayconExceptionDecoder {

    static final Map<Class<? extends PayconException>, ErrorAggangement> mapping = new HashMap<>();
    static
    {
        map(RecordNotFoundException.class, HTTP_NOT_FOUND, WAREHOUSE_NOT_FOUND, "RecordNotFound");
        map(UserAlreadyExistsException.class, HTTP_NOT_FOUND, USER_NOT_FOUND, "UserAlreadyExistsException");
        map(SubscriberNotFoundException.class, HTTP_NOT_FOUND, SUBSCRIBER_NOT_FOUND, "SubscriberNotFound");
        map(TransactionNotFoundException.class, HTTP_NOT_FOUND, TRANSACTION_NOT_FOUND, "TransactionNotFound");
        map(ProductNotExistsException.class, HTTP_NOT_FOUND, ACCOUNT_NOT_FOUND, "AccountnotFound");
        map(FeignUnknownException.class, HTTP_NOT_FOUND, UNKNOWN, "UnknownException");
        map(TenantAlreadyExists.class, HTTP_CONFLICT, TENANT_NOT_FOUND, "TenantAlreadyExists");
        map(SubscriberPendingException.class, HTTP_CONFLICT, PENDING_SUBSCRIPTION_REQUEST, "PendingSubscriptionRequest");
        map(ProductNotExistsException.class, HTTP_CONFLICT, PRODUCT_NOT_FOUND, "ProductNotFound");
        map(ActiveSubscriptionAlreadyExistException.class, HTTP_CONFLICT, SUBSCRIPTION_ALREADY_EXISTS, "ActiveSubscriptionAlreadyExists");
        map(InActiveSubscriptionAlreadyExistsException.class, HTTP_CONFLICT, SUBSCRIPTION_ALREADY_EXISTS, "InactiveSubscriptionAlreadyExists");
        map(TenantNotExistsException.class, HTTP_NOT_FOUND, TENANT_NOT_FOUND, "TenantNotFound");
        map(InvalidCredentialException.class, HTTP_UNAUTHORIZED, INVALID_CREDENTIALS, "InvalidCredentials");
        map(TransactionNotSaved.class, HTTP_PRECONDITION_FAILED, TRSACTION_FAILED, "TransactionFailed");
        map(ServiceTypeNotFoundException.class, HTTP_NOT_FOUND, SERVICE_TYPE_NOT_FOUND, "ServiceTypeNotFound");


    }

    public static ErrorAggangement getErrorArrgement(PayconException ex) {
        return mapping.get(ex.getClass());
    }
    private static void map(Class<? extends PayconException> exceptionClass, HttpErrorCode httpErrorCode, PayconErrorCode payconErrorCode, String errorArrangement) {
        if (mapping.containsKey(exceptionClass)) {
            throw new RuntimeException("Duplicated key: " + exceptionClass);
        }
        mapping.put(exceptionClass, new ErrorAggangement(httpErrorCode, payconErrorCode, errorArrangement));
    }
}

